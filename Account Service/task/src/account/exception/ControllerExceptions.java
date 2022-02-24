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

@ControllerAdvice
public class ControllerExceptions {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "User exist!") // NOTE : HttpStatus.CONFLICT, 409 preferred
    @ExceptionHandler(DataIntegrityViolationException.class)
    public void userExistsExceptionHandler() {

    }
    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Username/email not found")
    @ExceptionHandler(UsernameNotFoundException.class)
    public void usernameNotFoundExceptionHandler(){}

////    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Transaction system exception")
//    @ExceptionHandler(TransactionSystemException.class)
//    public ResponseEntity<Object> transactionSystemExceptionnHandler(TransactionSystemException ex){
//        System.out.println("DEBUG Transaction System Exception Hander: RE-THROWING original exception: " + ex.toString());
////        throw new RuntimeException(ex.getOriginalException());
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
//
//    }

//    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> constraintViolationExceptionnHandler(ConstraintViolationException ex){

        String message = ex.getMessage();
        return new ResponseEntity<>(message,  HttpStatus.BAD_REQUEST);

    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PasswordRequirementException.class)
    public ResponseEntity<String> passwordRequirementExceptionHandler(PasswordRequirementException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
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
