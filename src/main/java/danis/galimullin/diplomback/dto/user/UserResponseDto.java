package danis.galimullin.diplomback.dto.user;

import danis.galimullin.diplomback.model.User;

import java.util.Date;

public record UserResponseDto(
        String name,
        String email,
        Date createdAt,
        String avatarUrl,
        String backgroundUrl,
        String biography
) {
    static public UserResponseDto fromUser(User user) {
        return new UserResponseDto(user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getAvatarUrl(),
                user.getBackgroundUrl(),
                user.getBiography()
        );
    }
}
