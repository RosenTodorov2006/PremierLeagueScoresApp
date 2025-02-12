package bg.soft_uni.premierlegueapp.services.sheduling;

import bg.soft_uni.premierlegueapp.configuration.AiApiConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
@Component
public class KeepAliveService {
    private final RestClient restClient;
    private final AiApiConfiguration aiApiConfiguration;

    public KeepAliveService(AiApiConfiguration aiApiConfiguration) {
        this.aiApiConfiguration = aiApiConfiguration;
        this.restClient = RestClient.create();
    }

    @Scheduled(fixedRate = 60000) // На всеки 1 минути (600000 милисекунди)
    public void keepConnectionAlive() {
        String apiUrl = aiApiConfiguration.getApiUrl(); // Заменете с действителния OpenAI API endpoint
        String apiKey = aiApiConfiguration.getApiKey(); // Заменете с вашия API ключ
        try {
            // Изпращане на лека заявка (напр. GET заявка към /ping или друг endpoint)
            ResponseEntity<Void> response = this.restClient
                    .get()
                    .uri(apiUrl + "/ping") // Примерен endpoint за пинг
                    .header("Authorization", "Bearer " + apiKey)
                    .retrieve()
                    .toBodilessEntity();

            // Логване на успешна заявка
            System.out.println("Keep-alive request successful. Status: " + response.getStatusCode());
        } catch (Exception e) {
            // Логване на грешката
            System.err.println("Keep-alive request failed: " + e.getMessage());
        }
    }
}
