package danis.galimullin.diplomback.exception;

public class UserNameAlreadyExistsException extends RuntimeException {
    public UserNameAlreadyExistsException() {
        super("USER_NAME_ALREADY_EXISTS");
    }
}
