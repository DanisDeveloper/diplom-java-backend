package danis.galimullin.diplomback.service;

import danis.galimullin.diplomback.dto.user.UserLoginDto;
import danis.galimullin.diplomback.dto.user.UserStateDto;
import danis.galimullin.diplomback.dto.user.UserRegisterDto;
import danis.galimullin.diplomback.exception.DifferentPasswordException;
import danis.galimullin.diplomback.exception.UserEmailAlreadyExistsException;
import danis.galimullin.diplomback.exception.UserNameAlreadyExistsException;
import danis.galimullin.diplomback.exception.UserNotFoundException;
import danis.galimullin.diplomback.mapper.UserMapper;
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
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository,
                           AuthenticationManager authenticationManager,
                           UserMapper userMapper,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void registerUser(UserRegisterDto userRegisterDto) {
        if (userRepository.existsByEmail(userRegisterDto.email())) {
            throw new UserEmailAlreadyExistsException();
        }
        if (userRepository.existsByName(userRegisterDto.name())) {
            throw new UserNameAlreadyExistsException();
        }
        User user = userMapper.toEntity(userRegisterDto);
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
        return userMapper.toUserStateDto(optionalUser.get());
    }

    @Override
    @Transactional
    public void updatePassword(String username, String oldPassword, String newPassword) {
        User user = userRepository.findByName(username).orElseThrow(UserNotFoundException::new);
        if(!passwordEncoder.matches(oldPassword, user.getHashedPassword())){
            throw new DifferentPasswordException();
        }
        userRepository.updatePassword(username, passwordEncoder.encode(newPassword));
    }
}
