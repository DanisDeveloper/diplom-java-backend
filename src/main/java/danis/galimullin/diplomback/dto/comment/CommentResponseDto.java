package danis.galimullin.diplomback.dto.comment;

import danis.galimullin.diplomback.dto.shader.ShaderOriginDto;
import danis.galimullin.diplomback.dto.user.UserInfoForComment;

import java.util.Date;

public record CommentResponseDto(
        Long id,
        String text,
        Boolean hidden,
        Date createdAt,
        UserInfoForComment user,
        ShaderOriginDto shader
) {

}
