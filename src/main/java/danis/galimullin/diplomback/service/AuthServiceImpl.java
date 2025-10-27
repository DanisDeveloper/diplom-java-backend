package danis.galimullin.diplomback.service;

import danis.galimullin.diplomback.dto.user.UserLoginDto;
import danis.galimullin.diplomback.dto.user.UserStateDto;
import danis.galimullin.diplomback.dto.user.UserRegisterDto;
import danis.galimullin.diplomback.exception.UserAlreadyExistsException;
import danis.galimullin.diplomback.model.Role;
import danis.galimullin.diplomback.model.User;
import danis.galimullin.diplomback.repository.RoleRepository;
import danis.galimullin.diplomback.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           UserDetailsService userDetailsService,
                           AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    @Transactional
    public void registerUser(UserRegisterDto userRegisterDto) {
//        if (userRepository.existsByEmail(userRegisterDto.email())) {
//            var message = "Email: " + userRegisterDto.email() + " already exists";
//            throw new UserAlreadyExistsException(message);
//        }
        if (userRepository.existsByName(userRegisterDto.name())) {
            var message = "User with name '" + userRegisterDto.name() + "' already exists";
            throw new UserAlreadyExistsException(message);
        }
        User user = toEntity(userRegisterDto);
        userRepository.save(user);
    }

    @Override
    public void loginUser(UserLoginDto userLoginDto, HttpServletRequest request) {
//        var name = userLoginDto.name();
//        var password = userLoginDto.password();
//        if (userLoginDto.name().contains("@")) {
//
//        }
        var auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDto.name(), userLoginDto.password()));
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        request.getSession(true).setAttribute("SPRING_SECURITY_CONTEXT", context);
    }

    @Override
    public UserStateDto me(String username) {
        Optional<User> optionalUser = userRepository.findByName(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User '" + username + "' not found");
        }
        return UserStateDto.fromUser(optionalUser.get());
    }

    private User toEntity(UserRegisterDto userRegisterDto) {
        User user = new User();
        user.setName(userRegisterDto.name());
        user.setEmail(userRegisterDto.email());

        var hashedPassword = passwordEncoder.encode(userRegisterDto.password());
        user.setHashedPassword(hashedPassword);

        Role role = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new IllegalArgumentException("Role " + "ROLE_USER" + " not found"));
        user.setRoles(Set.of(role));
        return user;
    }
}
