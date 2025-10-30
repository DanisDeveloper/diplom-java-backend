package danis.galimullin.diplomback.exception;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException() {
        super("COMMENT_NOT_FOUND");
    }

}
