package danis.galimullin.diplomback.controller;

import danis.galimullin.diplomback.dto.user.UserProfileDto;
import danis.galimullin.diplomback.model.UserImageType;
import danis.galimullin.diplomback.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public UserProfileDto getUserProfileByUsername(@PathVariable String username) {
        return userService.getUserProfileByUsername(username);
    }

    @PatchMapping
    public void patchUserProfile(@RequestParam String biography, Principal principal) {
        userService.patchBiography(biography, principal.getName());
    }

    @PostMapping("/image")
    public String uploadFile(@RequestParam("type") UserImageType userImageType, @RequestParam("image") MultipartFile file, Principal principal) throws IOException {
        return userService.uploadUserImage(userImageType, file, principal.getName());
    }

    @DeleteMapping("/image")
    public void deleteFile(@RequestParam("type") UserImageType userImageType, Principal principal) throws IOException {
        userService.deleteUserImage(userImageType, principal.getName());
    }
}
