package bg.soft_uni.premierlegueapp.services.impl;

import bg.soft_uni.premierlegueapp.configuration.AiApiConfiguration;
import bg.soft_uni.premierlegueapp.services.AiApiConnection;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
public class AiApiConnectionImpl implements AiApiConnection {
    private final RestClient restClient;
    private final AiApiConfiguration aiApiConfiguration;

    public AiApiConnectionImpl(@Qualifier("generalRestClient") RestClient restClient, AiApiConfiguration aiApiConfiguration) {
        this.restClient = restClient;
        this.aiApiConfiguration = aiApiConfiguration;
    }


    @Override
    public String getResponse(List<String> messages) {
        String apiUrl = aiApiConfiguration.getApiUrl();
        String apiKey = aiApiConfiguration.getApiKey();
        String lastMessage = messages.get(messages.size() - 1).split(": ")[1];

        String requestBody = """
    {
        "model": "gpt-3.5-turbo",
        "messages": [
            {
                "role": "system",
                "content": "You are AI chatbot Tony, an assistant for football matches and teams in the English league only. Answer in the language of the question and keep responses as short as possible, max 10–20 words. Below is the conversation history: %s"
            },
            {
                "role": "user",
                "content": "%s"
            }
        ]
    }
    """.formatted(messages, lastMessage);

        int maxAttempts = 3;
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() ->
                    this.restClient
                            .post()
                            .uri(apiUrl)
                            .header("Authorization", "Bearer " + apiKey)
                            .header("Content-Type", "application/json")
                            .body(requestBody)
                            .retrieve()
                            .body(String.class)
            );
            try {
                // Изчакваме отговора до 25 секунди
                String responseBody = future.get(15, TimeUnit.SECONDS);
                if (responseBody == null || responseBody.isEmpty()) {
                    throw new RuntimeException("No response received from OpenAI API");
                }
                JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();
                JsonArray choices = jsonResponse.getAsJsonArray("choices");
                if (choices == null || choices.isEmpty()) {
                    throw new RuntimeException("No choices found in OpenAI API response");
                }
                JsonObject firstChoice = choices.get(0).getAsJsonObject();
                return firstChoice.getAsJsonObject("message").get("content").getAsString();
            } catch (TimeoutException te) {
                System.err.println("Request timed out on attempt " + attempt + ". Cancelling and retrying...");
                future.cancel(true); // Прекъсване на текущата заявка
                // Можеш да добавиш малко изчакване преди следващия опит, ако е необходимо:
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            } catch (Exception e) {
                throw new RuntimeException("Error making request: " + e.getMessage(), e);
            }
        }
        throw new RuntimeException("No response received from OpenAI API after " + maxAttempts + " attempts");
    }


    @Override
    public List<String> addMessage(List<String> messages, String message) {
        // Добавяме новото съобщение към съществуващите
        messages.add(message);

        // Получаваме отговор от AI на новото съобщение
        String aiResponse = getResponse(messages);
        messages.add("ai: " + aiResponse);  // Добавяме отговор от AI към съществуващите съобщения

        return messages;
    }
}
