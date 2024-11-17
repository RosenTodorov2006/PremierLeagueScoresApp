package bg.soft_uni.premierlegueapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AiController {
    @GetMapping("/ai")
    public String ai(){
        return "ai";
    }
}
