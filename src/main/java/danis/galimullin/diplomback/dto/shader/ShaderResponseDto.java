package danis.galimullin.diplomback.dto.shader;

import jakarta.annotation.Nullable;

import java.util.Date;

public record ShaderResponseDto(
        Long id,
        String title,
        String description,
        String code,
        Date createdAt,
        Date updatedAt,
        Boolean visibility,
        Long userId,
        @Nullable Long originId
) {
}
