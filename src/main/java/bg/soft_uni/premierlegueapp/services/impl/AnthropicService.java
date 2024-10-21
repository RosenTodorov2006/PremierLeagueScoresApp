package bg.soft_uni.premierlegueapp.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class AnthropicService {
    private final RestClient anthropicRestClient;
    private final ObjectMapper objectMapper;

    public AnthropicService(@Qualifier("anthropicRestClient") RestClient anthropicRestClient, ObjectMapper objectMapper) {
        this.anthropicRestClient = anthropicRestClient;
        this.objectMapper = objectMapper;
    }

    public String sendMessage(String message) {
        Map<String, Object> requestBody = Map.of(
                "model", "claude-3-sonnet-20240229",
                "max_tokens", 1024,
                "messages", List.of(Map.of("role", "user", "content", message))
        );

        try {
            String responseBody = anthropicRestClient
                    .post()
                    .uri("/v1/messages")
                    .body(requestBody)
                    .retrieve()
                    .toEntity(String.class)
                    .getBody();

            if (responseBody == null) {
                throw new RuntimeException("Empty response from API");
            }

            JsonNode jsonResponse = objectMapper.readTree(responseBody);
            return jsonResponse.path("content").path(0).path("text").asText();
        } catch (Exception e) {
            throw new RuntimeException("Error calling Anthropic API: " + e.getMessage(), e);
        }
    }
}
