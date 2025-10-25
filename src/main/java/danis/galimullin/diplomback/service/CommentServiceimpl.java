package danis.galimullin.diplomback.service;

import danis.galimullin.diplomback.repository.CommentRepository;
import org.springframework.stereotype.Service;


@Service
public class CommentServiceimpl implements CommentService {
    private final CommentRepository commentRepository;

    public CommentServiceimpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

}
