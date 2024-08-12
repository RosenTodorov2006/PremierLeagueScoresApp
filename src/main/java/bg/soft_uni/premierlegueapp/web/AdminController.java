package bg.soft_uni.premierlegueapp.web;

import bg.soft_uni.premierlegueapp.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String admin(Model model, Principal principal){
        model.addAttribute("users", userService.getAllUsers(principal.getName()));
        return "admin";
    }
    @PostMapping("/admin/{id}")
    public String changeRoleForUser(@PathVariable long id){
        this.userService.changeRole(id);
        return "redirect:/admin";
    }
}
