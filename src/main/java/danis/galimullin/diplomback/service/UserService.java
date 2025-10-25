package danis.galimullin.diplomback.service;

import danis.galimullin.diplomback.dto.user.UserRegisterDto;
import danis.galimullin.diplomback.dto.user.UserResponseDto;

import java.util.List;

public interface UserService {
    List<UserResponseDto> getAllUsers();
    UserResponseDto registerUser(UserRegisterDto userRegisterDto);
}
