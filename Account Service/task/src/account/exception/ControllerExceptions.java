package account.exception;

import account.exception.password.PasswordInsufficientLengthException;
import account.exception.password.PasswordPresentInDictionaryException;
import account.exception.password.PasswordReuseException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

@ControllerAdvice
public class ControllerExceptions {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "User exist!") // NOTE : HttpStatus.CONFLICT, 409 preferred
    @ExceptionHandler(DataIntegrityViolationException.class)
    public void userExistsExceptionHandler() {

    }
    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Username/email not found")
    @ExceptionHandler(UsernameNotFoundException.class)
    public void usernameNotFoundExceptionHandler(){}

//    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Transaction system exception")
    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<String> transactionSystemExceptionnHandler(TransactionSystemException ex){
        System.out.println("DEBUG Transaction System Exception Hander: getMessage " + ex.toString());
//        throw new RuntimeException(ex.getOriginalException());
        String rootcauseMessage = ex.getMessage();
//                getOriginalException().getMessage();
//                ex.getOriginalException().getCause().getMessage();
        return new ResponseEntity<>(rootcauseMessage, HttpStatus.BAD_REQUEST);

    }

    //    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> constraintViolationExceptionHandler(ConstraintViolationException ex){

        String message = "ConstraintViolationException" + ex.getMessage();
        return new ResponseEntity<>(ex.getConstraintViolations().toString(),  HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> validationExceptionHandler(ValidationException ex){

        String message = "ValidationException" + ex.getMessage();
        return new ResponseEntity<>(ex.toString(),  HttpStatus.BAD_REQUEST);

    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PasswordRequirementException.class)
    public ResponseEntity<String> passwordRequirementExceptionHandler(PasswordRequirementException ex){
        return new ResponseEntity<>("ex.getMessage()," , HttpStatus.BAD_REQUEST);
    }

//    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "The password length must be at leat 12 chars!")
//    @ExceptionHandler(PasswordInsufficientLengthException.class)
//    public void passwordInsufficientLengthExceptionHandler(){
//
//    }
//
//    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "The password is in the hacker's database!")
//    @ExceptionHandler(PasswordPresentInDictionaryException.class)
//    public void passwordPresentInDictionaryExceptionHandler(){
//
//    }
//
//    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "The passwords must be different!")
//    @ExceptionHandler(PasswordReuseException.class)
//    public void passwordReuseExceptionHandler(){
//
//    }





}