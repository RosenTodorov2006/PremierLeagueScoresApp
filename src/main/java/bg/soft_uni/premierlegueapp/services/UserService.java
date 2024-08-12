package bg.soft_uni.premierlegueapp.services;

import bg.soft_uni.premierlegueapp.models.dtos.LoginSeedDto;
import bg.soft_uni.premierlegueapp.models.dtos.RegisterSeedDto;
import bg.soft_uni.premierlegueapp.models.dtos.UserExportDto;
import bg.soft_uni.premierlegueapp.models.dtos.UserInfoForAdminDto;

import java.util.List;

public interface UserService {
    boolean isValidEmail(String email);

    void register(RegisterSeedDto registerSeedDto);


    UserExportDto getCurrentUserInfo(String email);

    boolean isValidUsername(String username);

    List<UserInfoForAdminDto> getAllUsers(String email);

    void changeRole(long id);

    void changeUsername(String email, String newUsername);
}
