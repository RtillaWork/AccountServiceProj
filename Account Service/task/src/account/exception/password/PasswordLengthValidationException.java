package account.exception.password;

import javax.validation.ConstraintDeclarationException;
import javax.validation.ValidationException;

public class PasswordLengthValidationException extends ConstraintDeclarationException { // ValidationException {
    public PasswordLengthValidationException(String message) {
        super(message);
    }

    public PasswordLengthValidationException() {
    }
}
