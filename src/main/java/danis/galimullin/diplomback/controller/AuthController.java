package danis.galimullin.diplomback.controller;

import danis.galimullin.diplomback.dto.user.UserLoginDto;
import danis.galimullin.diplomback.dto.user.UserStateDto;
import danis.galimullin.diplomback.dto.user.UserRegisterDto;
import danis.galimullin.diplomback.dto.user.UserResponseDto;
import danis.galimullin.diplomback.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterDto userRegisterDto) {
        authService.registerUser(userRegisterDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> loginUser(@RequestBody UserLoginDto userLoginDto, HttpServletRequest request) {
        authService.loginUser(userLoginDto, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserStateDto> me(Authentication authentication) {
        // TODO сделать PreAuthorize вместо условия
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(authService.me(authentication.getName()));
    }

    @PostMapping("/logout")
    public void logoutUser(HttpServletRequest request) {
        request.getSession().invalidate();
    }
}
