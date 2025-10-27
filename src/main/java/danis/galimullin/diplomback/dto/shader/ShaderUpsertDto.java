package danis.galimullin.diplomback.dto.shader;

import jakarta.annotation.Nullable;

public record ShaderUpsertDto(
        String title,
        String description,
        String code,
        Long userId,
        Boolean visibility,
        @Nullable Long originId
) { }
