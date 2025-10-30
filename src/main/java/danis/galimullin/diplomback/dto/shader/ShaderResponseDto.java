package danis.galimullin.diplomback.dto.shader;

import danis.galimullin.diplomback.dto.user.UserStateDto;

import java.util.Date;

public record ShaderResponseDto(
        Long id,
        String title,
        String description,
        String code,
        Date createdAt,
        Date updatedAt,
        Boolean visibility,
        Long views,
        UserStateDto user,
        ShaderOriginDto origin
) {
}
