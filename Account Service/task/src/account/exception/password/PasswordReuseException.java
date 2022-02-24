package account.exception.password;

import javax.validation.ValidationException;


        import org.springframework.security.core.AuthenticationException;

        import javax.validation.ValidationException;

public class PasswordReuseException extends ValidationException {
    public PasswordReuseException(String message) {
        super(message);
    }

    public PasswordReuseException() {
    }
}
