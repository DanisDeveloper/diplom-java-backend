package danis.galimullin.diplomback.service;

import danis.galimullin.diplomback.configuration.FileStorageProperties;
import danis.galimullin.diplomback.dto.user.UserProfileDto;
import danis.galimullin.diplomback.exception.FileEmptyException;
import danis.galimullin.diplomback.exception.UserNotFoundException;
import danis.galimullin.diplomback.mapper.UserMapper;
import danis.galimullin.diplomback.model.User;
import danis.galimullin.diplomback.model.UserImageType;
import danis.galimullin.diplomback.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final FileStorageProperties fileStorageProperties;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, FileStorageProperties fileStorageProperties) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.fileStorageProperties = fileStorageProperties;
    }

    @Override
    public UserProfileDto getUserProfileByUsername(String username) {
        User user = userRepository.findByName(username).orElseThrow(UserNotFoundException::new);
        return userMapper.toUserProfileDto(user);
    }

    @Override
    public void patchBiography(String biography, String username) {
        User user = userRepository.findByName(username).orElseThrow(UserNotFoundException::new);
        if (biography != null)
            user.setBiography(biography);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public String uploadUserImage(UserImageType type, MultipartFile file, String username) throws IOException {
        if (file.isEmpty()) {
            throw new FileEmptyException();
        }
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        String extension = "";
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex >= 0) {
            extension = filename.substring(dotIndex);
        }
        String randomFilename = UUID.randomUUID() + "." + extension;
        Path filepath = Paths.get(fileStorageProperties.getStaticFiles(), "/images", randomFilename);
        Files.createDirectories(filepath.getParent());
        Files.copy(file.getInputStream(), filepath, StandardCopyOption.REPLACE_EXISTING);

        switch (type) {
            case AVATAR -> userRepository.updateAvatar(username, randomFilename);
            case BACKGROUND -> userRepository.updateBackground(username, randomFilename);
        }

        return randomFilename;
    }

    @Override
    @Transactional
    public void deleteUserImage(UserImageType userImageType, String username) {
        switch (userImageType) {
            case AVATAR -> userRepository.updateAvatar(username, null);
            case BACKGROUND -> userRepository.updateBackground(username, null);
        }

    }


}
