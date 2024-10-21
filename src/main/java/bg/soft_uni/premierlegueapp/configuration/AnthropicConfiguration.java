package bg.soft_uni.premierlegueapp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
public class AnthropicConfiguration {
    private static final String API_KEY = "";

    @Bean("anthropicRestClient")
    public RestClient anthropicRestClient() {
        return RestClient
                .builder()
                .baseUrl("https://api.anthropic.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("x-api-key", API_KEY)
                .defaultHeader("anthropic-version", "2023-06-01")
                .build();
    }
}
