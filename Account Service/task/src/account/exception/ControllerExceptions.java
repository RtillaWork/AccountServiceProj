package account.exception;

import account.exception.password.PasswordLengthValidationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

@ControllerAdvice
public class ControllerExceptions {

    public static final String MESSAGES_DELIMITER = ", ";

    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "User exist!") // NOTE : HttpStatus.CONFLICT, 409 preferred
    @ExceptionHandler(DataIntegrityViolationException.class)
    public void userExistsExceptionHandler() {

    }
    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Username/email not found")
    @ExceptionHandler(UsernameNotFoundException.class)
    public void usernameNotFoundExceptionHandler(){}

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Transaction system exception")
    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<String> transactionSystemExceptionnHandler(TransactionSystemException ex){
        System.out.println("DEBUG Transaction System Exception Hander: getMessage " + ex.toString());
//        throw new RuntimeException(ex.getOriginalException());
        String rootcauseMessage = ex.getMessage();
//                getOriginalException().getMessage();
//                ex.getOriginalException().getCause().getMessage();
        return new ResponseEntity<>(rootcauseMessage, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<String> constraintViolationExceptionHandler(ConstraintViolationException ex){
        System.out.println("ConstraintViolationException " + ex.getMessage() + "and its Class: " + ex.getClass());
//        String message = "ConstraintViolationException" + ex.getMessage();
//        List<String> messages;
        var messages = ex.getConstraintViolations().stream()
                .map(constraintViolation -> constraintViolation.getMessage())
                .reduce((constraintViolationMsg, constraintViolationMsg2) -> {
                    var msg = constraintViolationMsg + MESSAGES_DELIMITER + constraintViolationMsg2;
                    return msg;
                })
                .orElse("DEBUG UNEXPECTED EXCEPTION DURING CONSTRAINTVIOLATIONS .getMessage()");

        return new ResponseEntity<>(messages,  HttpStatus.BAD_REQUEST);

    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<String> constraintViolationExceptionHandler(MethodArgumentNotValidException ex){
        System.out.println("MethodArgumentNotValidException " + ex.getMessage() + "and its Class: " + ex.getClass().getSimpleName());
//        String message = "ConstraintViolationException" + ex.getMessage();
//        List<String> messages;
        var messages = ex.getMessage();

        return new ResponseEntity<>(messages,  HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> validationExceptionHandler(ValidationException ex){

//        switch (ex.getClass()) {
//            case
//        }

               System.out.println("ValidationException: " + ex.getMessage() + "and its Class: " + ex.getClass());

        return new ResponseEntity<>(ex.toString(),  HttpStatus.BAD_REQUEST);

    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PasswordRequirementException.class)
    public ResponseEntity<String> passwordRequirementExceptionHandler(PasswordRequirementException ex){
        return new ResponseEntity<>("ex.getMessage()," , HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "The password length must be at leat 12 chars!")
    @ExceptionHandler(PasswordLengthValidationException.class)
    public void passwordLengthValidationExceptionHandler(){

    }

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