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

        // Създаване на заявката с контекста
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
        """.formatted(messages, lastMessage);  // Записваме последното съобщение на потребителя

        // Изпращане на заявката с RestClient
        String responseBody = this.restClient
                .post()
                .uri(apiUrl)
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .body(requestBody)
                .retrieve()
                .body(String.class);

        if (responseBody == null || responseBody.isEmpty()) {
            throw new RuntimeException("No response received from OpenAI API");
        }

        // Обработка на JSON отговора
        JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();
        JsonArray choices = jsonResponse.getAsJsonArray("choices");

        if (choices == null || choices.isEmpty()) {
            throw new RuntimeException("No choices found in OpenAI API response");
        }

        JsonObject firstChoice = choices.get(0).getAsJsonObject();
        String aiMessage = firstChoice.getAsJsonObject("message").get("content").getAsString();

        return aiMessage;
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
