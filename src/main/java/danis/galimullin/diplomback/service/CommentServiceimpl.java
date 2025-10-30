package danis.galimullin.diplomback.service;

import danis.galimullin.diplomback.dto.comment.CommentResponseDto;
import danis.galimullin.diplomback.exception.CommentNotFoundException;
import danis.galimullin.diplomback.exception.ShaderNotFoundException;
import danis.galimullin.diplomback.exception.UserNotFoundException;
import danis.galimullin.diplomback.mapper.CommentMapper;
import danis.galimullin.diplomback.model.Comment;
import danis.galimullin.diplomback.model.Shader;
import danis.galimullin.diplomback.model.User;
import danis.galimullin.diplomback.repository.CommentRepository;
import danis.galimullin.diplomback.repository.ShaderRepository;
import danis.galimullin.diplomback.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CommentServiceimpl implements CommentService {
    private final CommentRepository commentRepository;
    private final ShaderRepository shaderRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    public CommentServiceimpl(CommentRepository commentRepository, ShaderRepository shaderRepository, UserRepository userRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.shaderRepository = shaderRepository;
        this.userRepository = userRepository;
        this.commentMapper = commentMapper;
    }

    @Override
    @Transactional
    public CommentResponseDto commentShader(Long shaderId, String text, String username) {
        Shader shader = shaderRepository.findById(shaderId).orElseThrow(ShaderNotFoundException::new);
        User user = userRepository.findByName(username).orElseThrow(UserNotFoundException::new);

        Comment comment = new Comment();
        comment.setShader(shader);
        comment.setUser(user);
        comment.setText(text);

        commentRepository.save(comment);
        return commentMapper.toCommentResponseDto(comment);
    }

    @Override
    public void setHiddenCommentStatus(Long commentId, Boolean hidden) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        comment.setHidden(hidden);
        commentRepository.save(comment);
    }

    @Override
    public List<CommentResponseDto> getAllShaderComments(Long shaderId) {
        return commentRepository
                .findAllByShaderId(shaderId).stream()
                .map(commentMapper::toCommentResponseDto)
                .collect(Collectors.toList());
    }

}
