package account.security.entity;

import account.service.validation.PasswordLengthValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PasswordLengthValidator.class)
public @interface PasswordLengthValidation {
    // default error message
    public String message() default "The password length is invalid";
    // groups
    public Class<?>[] groups() default {};
    // payload
    public Class<? extends Payload>[] payload() default {};


}
