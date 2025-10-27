package danis.galimullin.diplomback.service;

import danis.galimullin.diplomback.dto.user.UserResponseDto;
import danis.galimullin.diplomback.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream().map(UserResponseDto::fromUser).collect(Collectors.toList());
    }
}
