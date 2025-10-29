package danis.galimullin.diplomback.exception;

public class LikeAlreadyExists extends RuntimeException {
    public LikeAlreadyExists() {
        super("LIKE_ALREADY_EXISTS");
    }
}
