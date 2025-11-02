package danis.galimullin.diplomback.repository;

import danis.galimullin.diplomback.dto.comment.CommentResponseDto;
import danis.galimullin.diplomback.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByShaderIdOrderByCreatedAtDesc(Long shaderId);
}
