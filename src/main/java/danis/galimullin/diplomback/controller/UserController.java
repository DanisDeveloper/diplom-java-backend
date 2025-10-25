package danis.galimullin.diplomback.controller;

import danis.galimullin.diplomback.dto.user.UserRegisterDto;
import danis.galimullin.diplomback.dto.user.UserResponseDto;
import danis.galimullin.diplomback.model.User;
import danis.galimullin.diplomback.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public UserResponseDto registerUser(@RequestBody UserRegisterDto userRegisterDto) {
        return userService.registerUser(userRegisterDto);
    }
}
