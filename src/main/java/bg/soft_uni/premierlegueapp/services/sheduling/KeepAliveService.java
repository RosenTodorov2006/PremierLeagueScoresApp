package bg.soft_uni.premierlegueapp.services.sheduling;

import bg.soft_uni.premierlegueapp.configuration.AiApiConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import java.util.Map;

@Component
public class KeepAliveService {
    private final RestClient restClient;
    private final AiApiConfiguration aiApiConfiguration;

    public KeepAliveService(AiApiConfiguration aiApiConfiguration) {
        this.aiApiConfiguration = aiApiConfiguration;
        this.restClient = RestClient.create();
    }

    @Scheduled(fixedRate = 60000) // На всеки 1 минута
    public void keepConnectionAlive() {
        String apiUrl = aiApiConfiguration.getApiUrl(); // OpenAI API URL
        String apiKey = aiApiConfiguration.getApiKey(); // OpenAI API Key

        // Малка dummy заявка
        Map<String, Object> requestBody = Map.of(
                "model", "gpt-3.5-turbo",
                "messages", new Object[]{
                        Map.of("role", "system", "content", "This is a keep-alive request."),
                        Map.of("role", "user", "content", "ping")
                },
                "max_tokens", 1 // Минимален отговор, за да не хабим ресурси
        );

        try {
            ResponseEntity<String> response = this.restClient
                    .post()
                    .uri(apiUrl) // Реалният OpenAI endpoint
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .body(requestBody)
                    .retrieve()
                    .toEntity(String.class);

            System.out.println("Keep-alive request successful. Status: " + response.getStatusCode());
        } catch (Exception e) {
            System.err.println("Keep-alive request failed: " + e.getMessage());
        }
    }
}
