package danis.galimullin.diplomback.mapper;

import danis.galimullin.diplomback.dto.comment.CommentResponseDto;
import danis.galimullin.diplomback.model.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    private final UserMapper userMapper;
    private final ShaderMapper shaderMapper;

    public CommentMapper(UserMapper userMapper, ShaderMapper shaderMapper) {
        this.userMapper = userMapper;
        this.shaderMapper = shaderMapper;
    }

    public CommentResponseDto toCommentResponseDto(Comment comment) {
        return new CommentResponseDto(
                comment.getId(),
                comment.getHidden() ? "" : comment.getText(),
                comment.getHidden(),
                comment.getCreatedAt(),
                userMapper.toUserResponseDto(comment.getUser()),
                shaderMapper.toShaderOriginDto(comment.getShader())
        );
    }

}
