package account.exception;

import account.exception.password.PasswordLengthValidationException;
import org.springframework.boot.context.properties.bind.validation.ValidationErrors;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@ControllerAdvice
public class ControllerExceptions extends ResponseEntityExceptionHandler {

    public static final String MESSAGES_DELIMITER = ", ";

    /**
     * Customize the response for MethodArgumentNotValidException.
     * <p>This method delegates to {@link #handleExceptionInternal}.
     *
     * @param ex      the exception
     * @param headers the headers to be written to the response
     * @param status  the selected response status
     * @param request the current request
     * @return a {@code ResponseEntity} instance
     */
    @Override
    protected ResponseEntity<Object>
    handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        return super.handleMethodArgumentNotValid(ex, headers, status, request);
        Map<String, Object> responseEntityBody = new LinkedHashMap<>();
        responseEntityBody.put("timestamp", new Date());
        responseEntityBody.put("status", status.value());
        responseEntityBody.put("error", status.getReasonPhrase());
        responseEntityBody.put("path", extractPathFromURI(request.getDescription(false)));

        List<String> violations = ex.getBindingResult().getFieldErrors()
                .stream().map(fieldError -> fieldError.getDefaultMessage())
                .collect(Collectors.toList());
        if (violations.size() == 0) {
            responseEntityBody.put("message", "ERROR: UNDEFINED MethodArgumentNotValidException ((violations.size() == 0))");
        } else if (violations.size() == 1) {
            responseEntityBody.put("message", violations.stream().findFirst().get());
        } else {
//            responseEntityBody.put("message", violations.toString());
            responseEntityBody.put("message", violations.stream().findFirst().get());

        }

        return new ResponseEntity<>(responseEntityBody, headers, status);
    }

    /**
     * Customize the response for BindException.
     * <p>This method delegates to {@link #handleExceptionInternal}.
     *
     * @param ex      the exception
     * @param headers the headers to be written to the response
     * @param status  the selected response status
     * @param request the current request
     * @return a {@code ResponseEntity} instance
     */
    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleBindException(ex, headers, status, request);
    }

    /**
     * Customize the response for NoHandlerFoundException.
     * <p>This method delegates to {@link #handleExceptionInternal}.
     *
     * @param ex      the exception
     * @param headers the headers to be written to the response
     * @param status  the selected response status
     * @param request the current request
     * @return a {@code ResponseEntity} instance
     * @since 4.0
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleNoHandlerFoundException(ex, headers, status, request);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "User exist!") // NOTE : HttpStatus.CONFLICT, 409 preferred
    @ExceptionHandler(DataIntegrityViolationException.class)
    public void userExistsExceptionHandler() {

    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Username/email not found")
    @ExceptionHandler(UsernameNotFoundException.class)
    public void usernameNotFoundExceptionHandler() {
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Transaction system exception")
    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<String> transactionSystemExceptionnHandler(TransactionSystemException ex) {
        System.out.println("DEBUG Transaction System Exception Hander: getMessage " + ex.toString());
//        throw new RuntimeException(ex.getOriginalException());
        String rootcauseMessage = ex.getMessage();
//                getOriginalException().getMessage();
//                ex.getOriginalException().getCause().getMessage();
        return new ResponseEntity<>(rootcauseMessage, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<String> constraintViolationExceptionHandler(ConstraintViolationException ex) {
        System.out.println("ConstraintViolationException " + ex.getMessage() + "and its Class: " + ex.getClass());
//        String message = "ConstraintViolationException" + ex.getMessage();"ConstraintViolationException" + ex.getMessage();
//        String message = "";
//        List<String> messages;
//        Set<ConstraintViolation<?>> cv = ex.getConstraintViolations();
//        if (!cv.isEmpty()) {
//            cv.stream().map(constraintViolation -> constraintViolation.getMessage())
//                    .reduce()
//        } else {
//            return new ResponseEntity<>("EXCEPTION: SOME UNSPECIFIED CONSTRAINT VIOLATION HAS OCCURED",  HttpStatus.BAD_REQUEST);
//        }

        var messages = ex.getConstraintViolations().stream()
                .map(constraintViolation -> constraintViolation.getMessage())
                .reduce((constraintViolationMsg, constraintViolationMsg2) -> {
                    var msg = constraintViolationMsg + MESSAGES_DELIMITER + constraintViolationMsg2;
                    return msg;
                })
                .orElse("DEBUG UNEXPECTED EXCEPTION DURING CONSTRAINTVIOLATIONS .getMessage()");

        return new ResponseEntity<>(messages, HttpStatus.BAD_REQUEST);

    }

//    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
//    @ExceptionHandler({MethodArgumentNotValidException.class})
//    public ResponseEntity<String> constraintViolationExceptionHandler(MethodArgumentNotValidException ex){
//        System.out.println("MethodArgumentNotValidException " + ex.getMessage() + "and its Class: " + ex.getClass().getSimpleName());
////        String message = "ConstraintViolationException" + ex.getMessage();
////        List<String> messages;
//        var messages = ex.getMessage();
//        return new ResponseEntity<>(messages,  HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(ValidationException.class)
//    public ResponseEntity<String> validationExceptionHandler(ValidationException ex){
////        switch (ex.getClass()) {
////            case
////        }
//               System.out.println("ValidationException: " + ex.getMessage() + "and its Class: " + ex.getClass());
//        return new ResponseEntity<>(ex.toString(),  HttpStatus.BAD_REQUEST);
//    }

//    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(PasswordRequirementException.class)
//    public ResponseEntity<String> passwordRequirementExceptionHandler(PasswordRequirementException ex){
//        return new ResponseEntity<>("ex.getMessage()," , HttpStatus.BAD_REQUEST);
//    }
//
//    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "The password length must be at leat 12 chars!")
//    @ExceptionHandler(PasswordLengthValidationException.class)
//    public void passwordLengthValidationExceptionHandler(){
//
//    }

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


//    /**
//     * Build list of ValidationError from set of ConstraintViolation
//     *
//     * @param violations Set<ConstraintViolation<?>> - Set
//     * of parameterized ConstraintViolations
//     * @return List<ValidationError> - list of validation errors
//     */
//    private List<ValidationErrors> buildValidationErrors(
//            Set<ConstraintViolation<?>> violations) {
//        return violations.
//                stream().
//                map(violation ->
//                        ValidationErrors.builder().
//                                field(
//                                        StreamSupport.stream(
//                                                        violation.getPropertyPath().spliterator(), false).
//                                                reduce((first, second) -> second).
//                                                orElse(null).
//                                                toString()
//                                ).
//                                error(violation.getMessage()).
//                                build()).
//                collect(toList());
//    }
//

    private String extractPathFromURI(String requestDescriptionWithURI) {
        String[] path = requestDescriptionWithURI.split("uri=");
        return path[1];
    }

}