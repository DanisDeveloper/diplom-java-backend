package danis.galimullin.diplomback.dto.user;

import java.util.Date;

public record UserResponseDto(
        Long id,
        String name,
        String email,
        Date createdAt,
        String avatarUrl,
        String backgroundUrl,
        String biography
) {

}
