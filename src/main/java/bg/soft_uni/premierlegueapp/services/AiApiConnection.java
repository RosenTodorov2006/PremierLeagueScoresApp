package bg.soft_uni.premierlegueapp.services;

import java.util.List;
import java.util.Map;

public interface AiApiConnection {
    String getResponse(List<String> prompt);

    List<String> addMessage(List<String> messages, String message);

}
