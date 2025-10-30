package danis.galimullin.diplomback.service;

import danis.galimullin.diplomback.dto.comment.CommentResponseDto;

import java.util.List;

public interface CommentService {
    CommentResponseDto commentShader(Long shaderId, String text, String username);
    void setHiddenCommentStatus(Long commentId, Boolean hidden);
    List<CommentResponseDto> getAllShaderComments(Long shaderId);
}
