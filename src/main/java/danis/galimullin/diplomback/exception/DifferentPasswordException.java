package danis.galimullin.diplomback.exception;

public class DifferentPasswordException extends RuntimeException {
    public DifferentPasswordException() {
        super("DIFFERENT_PASSWORD");
    }
}
