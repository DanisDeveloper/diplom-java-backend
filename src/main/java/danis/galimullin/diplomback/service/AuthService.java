package danis.galimullin.diplomback.service;

import danis.galimullin.diplomback.dto.user.UserLoginDto;
import danis.galimullin.diplomback.dto.user.UserStateDto;
import danis.galimullin.diplomback.dto.user.UserRegisterDto;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    void registerUser(UserRegisterDto userRegisterDto);
    void loginUser(UserLoginDto userLoginDto, HttpServletRequest request);
    UserStateDto me(String username);
    void updatePassword(String username, String oldPassword, String newPassword);
}
