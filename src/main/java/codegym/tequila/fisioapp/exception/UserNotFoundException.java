package codegym.tequila.fisioapp.exception;

public class UserNotFoundException extends InvalidCredentialsException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
