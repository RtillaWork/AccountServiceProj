package account.exception.password;

import javax.validation.ValidationException;

public class PasswordLengthValidationException extends ValidationException {
    public PasswordLengthValidationException(String message) {
        super(message);
    }

    public PasswordLengthValidationException() {
    }
}
