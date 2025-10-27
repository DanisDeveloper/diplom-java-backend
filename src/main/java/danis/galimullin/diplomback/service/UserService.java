package danis.galimullin.diplomback.service;

import danis.galimullin.diplomback.dto.user.UserResponseDto;

import java.util.List;

public interface UserService {
    List<UserResponseDto> getAllUsers();
}
