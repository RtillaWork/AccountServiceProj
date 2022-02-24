package account.exception.password;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptions {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "User exist!") // NOTE : HttpStatus.CONFLICT, 409 preferred
    @ExceptionHandler(DataIntegrityViolationException.class)
    public void userExistsExceptionHandler() {

    }
    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Username/email not found")
    @ExceptionHandler(UsernameNotFoundException.class)
    public void usernameNotFoundExceptionHandler(){}

    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Transaction system exception")
    @ExceptionHandler(TransactionSystemException.class)
    public void transactionSystemExceptionnHandler(){
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "The password length must be at leat 12 chars!")
    @ExceptionHandler(PasswordInsufficientLengthException.class)
    public void passwordInsufficientLengthExceptionHandler(){

    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "The password is in the hacker's database!")
    @ExceptionHandler(PasswordPresentInDictionaryException.class)
    public void passwordPresentInDictionaryExceptionHandler(){

    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "The passwords must be different!")
    @ExceptionHandler(PasswordReuseException.class)
    public void passwordReuseExceptionHandler(){

    }





}
