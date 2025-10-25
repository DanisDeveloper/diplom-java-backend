package danis.galimullin.diplomback.service;

import danis.galimullin.diplomback.dto.user.UserRegisterDto;
import danis.galimullin.diplomback.dto.user.UserResponseDto;
import danis.galimullin.diplomback.model.Role;
import danis.galimullin.diplomback.model.User;
import danis.galimullin.diplomback.repository.RoleRepository;
import danis.galimullin.diplomback.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserResponseDto registerUser(UserRegisterDto userRegisterDto) {
        if (userRepository.findByEmail(userRegisterDto.email()).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }
        User user = toEntity(userRegisterDto);
        User registeredUser = userRepository.save(user);
        return toDto(registeredUser);
    }

    private User toEntity(UserRegisterDto userRegisterDto) {
        User user = new User();
        user.setName(userRegisterDto.name());
        user.setEmail(userRegisterDto.email());

        var hashedPassword = passwordEncoder.encode(userRegisterDto.password());
        user.setHashedPassword(hashedPassword);

        Role role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new IllegalArgumentException("Role " + "ROLE_USER" + " not found"));
        user.setRoles(Set.of(role));
        return user;
    }

    private UserResponseDto toDto(User user) {
        return new UserResponseDto(
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getAvatarUrl(),
                user.getBackgroundUrl(),
                user.getBiography()
        );
    }
}
