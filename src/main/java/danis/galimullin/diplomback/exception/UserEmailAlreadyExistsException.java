package danis.galimullin.diplomback.exception;

public class UserEmailAlreadyExistsException extends RuntimeException {
    public UserEmailAlreadyExistsException() {
        super("USER_EMAIL_ALREADY_EXISTS");
    }
}
