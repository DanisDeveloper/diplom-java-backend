package danis.galimullin.diplomback.dto.shader;

import jakarta.annotation.Nullable;

public record ShaderCreateDto(
        String title,
        String description,
        String code,
        Long userId,
        @Nullable Long originId
) { }
