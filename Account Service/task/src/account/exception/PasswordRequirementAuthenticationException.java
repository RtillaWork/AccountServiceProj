package account.exception;

import org.springframework.security.core.AuthenticationException;

import javax.validation.ValidationException;

public class PasswordRequirementAuthenticationException extends ValidationException {
    public PasswordRequirementAuthenticationException(String message) {
        super(message);
    }

    public PasswordRequirementAuthenticationException() {
        super();
    }
}
