package danis.galimullin.diplomback.service;

import danis.galimullin.diplomback.dto.user.UserProfileDto;
import danis.galimullin.diplomback.model.UserImageType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    UserProfileDto getUserProfileByUsername(String username);

    void patchBiography(String biography, String username);

    String uploadUserImage(UserImageType type, MultipartFile file, String username) throws IOException;

    void deleteUserImage(UserImageType userImageType, String username);
}