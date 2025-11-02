package danis.galimullin.diplomback.exception;

public class FileEmptyException extends RuntimeException {
    public FileEmptyException() {
        super("FILE_EMPTY");
    }
}
