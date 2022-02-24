package account.exception.password;

import javax.validation.ValidationException;

public class PasswordPresentInDictionaryException extends ValidationException {
    public PasswordPresentInDictionaryException(String message) {
        super(message);
    }

    public PasswordPresentInDictionaryException() {
    }
}
