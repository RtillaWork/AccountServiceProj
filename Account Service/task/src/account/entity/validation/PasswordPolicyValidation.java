package account.entity.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PasswordPolicyValidator.class)
public @interface PasswordPolicyValidation {

    // default error message
    public String message() default "The password is in the hacker's database!";

    // groups
    public Class<?>[] groups() default {};

    // payload
    public Class<? extends Payload>[] payload() default {};

    // default min  and max sizes, inclusive
    String[] weakPasswordDictionary() default {"PasswordForJanuary", "PasswordForFebruary", "PasswordForMarch", "PasswordForApril",
            "PasswordForMay", "PasswordForJune", "PasswordForJuly", "PasswordForAugust",
            "PasswordForSeptember", "PasswordForOctober", "PasswordForNovember", "PasswordForDecember"};

}
