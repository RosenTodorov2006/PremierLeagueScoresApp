package bg.soft_uni.premierlegueapp.services.impl;

import bg.soft_uni.premierlegueapp.configuration.AiApiConfiguration;
import bg.soft_uni.premierlegueapp.services.AiApiConnection;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class AiApiConnectionImpl implements AiApiConnection {
    private final RestClient restClient;
    private final AiApiConfiguration aiApiConfiguration;

    public AiApiConnectionImpl(@Qualifier("generalRestClient") RestClient restClient, AiApiConfiguration aiApiConfiguration) {
        this.restClient = restClient;
        this.aiApiConfiguration = aiApiConfiguration;
    }

    public String getResponse(String prompt) {
        String apiUrl = aiApiConfiguration.getApiUrl();
        String apiKey = aiApiConfiguration.getApiKey();

        // Създаване на JSON заявка като String
        String requestBody = """
            {
                "model": "gpt-3.5-turbo",
                "messages": [
                    {"role": "system", "content": "You are an assistant for football matches and teams related only to the English league. Answer in the same language in which the question is asked."},
                    {"role": "user", "content": "%s"}
                ]
            }
            """.formatted(prompt);
        // Изпращане на заявка с RestClient
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
        JsonObject message = firstChoice.getAsJsonObject("message");
        return message.get("content").getAsString();
    }
}
