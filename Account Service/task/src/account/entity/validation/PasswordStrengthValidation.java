package account.entity.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PasswordStrengthValidator.class)
public @interface PasswordStrengthValidation {
    // default error message
    public String message() default "The password length must be at least 12 chars!";

    // groups
    public Class<?>[] groups() default {};

    // payload
    public Class<? extends Payload>[] payload() default {};

    // default min  and max sizes, inclusive
    int min() default 12; // 12 characters is the smallest acceptable password char size

    int max() default Byte.MAX_VALUE;
}