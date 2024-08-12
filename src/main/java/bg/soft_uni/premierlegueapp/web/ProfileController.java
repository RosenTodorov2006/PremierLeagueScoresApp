package bg.soft_uni.premierlegueapp.web;

import bg.soft_uni.premierlegueapp.models.dtos.NewUsernameSeedDto;
import bg.soft_uni.premierlegueapp.services.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
@Controller
public class ProfileController {
    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String profile(Model model, Principal principal){
        model.addAttribute("userExportDto", this.userService.getCurrentUserInfo(principal.getName()));
        if(!model.containsAttribute("newUsernameSeedDto")){
            model.addAttribute("newUsernameSeedDto", new NewUsernameSeedDto());
        }
        return "profile";
    }
    @PostMapping("/profile")
    public String changeUsername(Principal principal, @Valid NewUsernameSeedDto newUsernameSeedDto, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.newUsernameSeedDto", bindingResult);
            redirectAttributes.addFlashAttribute("newUsernameSeedDto", newUsernameSeedDto);
            return "redirect:/profile";
        }
        this.userService.changeUsername(principal.getName(), newUsernameSeedDto.getNewUsername());
        return "redirect:/profile";
    }
}
