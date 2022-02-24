package account.exception;

import javax.validation.ValidationException;

public class PasswordRequirementException extends ValidationException {
    public PasswordRequirementException(String message) {
        super(message);
    }

    public PasswordRequirementException() {
    }

    public PasswordRequirementException(Throwable cause) {
        super(cause);
    }

    public PasswordRequirementException(String message, Throwable cause) {
//        super(message, cause);
        cause.getMessage();
    }
}
