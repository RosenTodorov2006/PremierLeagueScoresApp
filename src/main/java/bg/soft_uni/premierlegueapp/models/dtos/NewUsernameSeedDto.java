package bg.soft_uni.premierlegueapp.models.dtos;

import bg.soft_uni.premierlegueapp.validation.annotations.UniqueUsername;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class NewUsernameSeedDto {
    @UniqueUsername
    @NotBlank(message = "Username not must be empty!")
    @Size(min = 3, max = 20, message = "Username length must be between 3 and 20 symbols!")
    private String newUsername;

    public String getNewUsername() {
        return newUsername;
    }

    public void setNewUsername(String newUsername) {
        this.newUsername = newUsername;
    }
}
