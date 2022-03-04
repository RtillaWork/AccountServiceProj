package account.entity.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PasswordNonReusePolicyValidator.class)
public @interface PasswordNonReusePolicyValidation {
    // default error message
    public String message() default "The passwords must be different!";

    // groups
    public Class<?>[] groups() default {};

    // payload
    public Class<? extends Payload>[] payload() default {};

    //
    String nonReusePassword() default "";


}