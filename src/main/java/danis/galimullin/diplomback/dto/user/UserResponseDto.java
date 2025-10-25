package danis.galimullin.diplomback.dto.user;

import java.util.Date;

public record UserResponseDto(
        String name,
        String email,
        Date createdAt,
        String avatarUrl,
        String backgroundUrl,
        String biography
) {
}
