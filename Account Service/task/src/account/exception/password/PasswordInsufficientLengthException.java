package account.exception.password;

import javax.validation.ValidationException;

public class PasswordInsufficientLengthException extends ValidationException {
    public PasswordInsufficientLengthException(String message) {
        super(message);
    }

    public PasswordInsufficientLengthException() {
    }
}
