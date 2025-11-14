package danis.galimullin.diplomback.mapper;

import danis.galimullin.diplomback.dto.user.*;
import danis.galimullin.diplomback.model.Role;
import danis.galimullin.diplomback.model.User;
import danis.galimullin.diplomback.repository.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class UserMapper {
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserMapper(PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public User toEntity(UserRegisterDto userRegisterDto) {
        User user = new User();
        user.setName(userRegisterDto.name());
        user.setEmail(userRegisterDto.email());

        var hashedPassword = passwordEncoder.encode(userRegisterDto.password());
        user.setHashedPassword(hashedPassword);

        Role role = roleRepository
                .findByName("ROLE_USER")
                .orElseThrow(() -> new IllegalArgumentException("Role " + "ROLE_USER" + " not found"));
        user.setRoles(Set.of(role));
        return user;
    }

    public UserInfoForComment toUserInfoForComment(User user) {
        return new UserInfoForComment(
                user.getId(),
                user.getName(),
                user.getAvatarUrl()
        );
    }

    public UserStateDto toUserStateDto(User user) {
        return new UserStateDto(
                user.getId(),
                user.getName()
        );
    }
}
