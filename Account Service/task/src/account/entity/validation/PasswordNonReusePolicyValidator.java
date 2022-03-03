package account.entity.validation;

import account.security.PasswordEncoderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.security.Principal;


public class PasswordNonReusePolicyValidator implements ConstraintValidator<PasswordNonReusePolicyValidation, String> {

    // this value should be the most random and represent an absence of prior passwords in DB, on initial User creation for ex.
    public static final String NOTYET_OR_NULL_nonReusePassword = "DEBUG_NO_OR_INITIAL_CUSER_CREATION_NULL_PASSWORD";

    @Autowired
    PasswordEncoderImpl passwordEncoder;

    @Autowired
    UserDetailsService userDetailsService;

    //    @Inject private Principal principal;
    private Principal principal;

    private String nonReusePassword = NOTYET_OR_NULL_nonReusePassword;
    private String message;


    /**
     * Implements the validation logic.
     * The state of {@code value} must not be altered.
     * <p>
     * This method can be accessed concurrently, thread-safety must be ensured
     * by the implementation.
     *
     * @param value   object to validate
     * @param context context in which the constraint is evaluated
     * @return {@code false} if {@code value} does not pass the constraint
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        boolean isValid = false;

        if (value == null) {
            // TODO: should this throw a ConstraintValidationException?
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            System.out.println("DEBUG ISVALID VALUE IS NULL = " + value);
            isValid = true;// TODO the case of null password should be handled by a separate annotation, so ignore null buy returning true

        } else if (nonReusePassword == null || nonReusePassword == NOTYET_OR_NULL_nonReusePassword) {
            // TODO the case of null password should be handled by a separate annotation, so ignore null buy returning true
            // TODO WIP almost same as above, make valid to ignore annotation
            isValid = true;
        } else {
            // TODO Warning providing a nonReusePassword shortcircuits the last else check against DB prev password(s).
            int isPasswordReused = passwordEncoder.passwordEncoder().matches(value, nonReusePassword) ? 1 : 0;
            switch (isPasswordReused) {
                case 1: {
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
                    System.out.println("DEBUG ISVALID VALUE throw new PasswordNonReusePolicyValidation(); = " + value);
                    isValid = false;
                    break;
                }
                case 0: {
                    isValid = true;
                    break;
                }
            }

        }
        return isValid;
    }


    /**
     * Initializes the validator in preparation for
     * {@link #isValid(Object, ConstraintValidatorContext)} calls.
     * The constraint annotation for a given constraint declaration
     * is passed.
     * <p>
     * This method is guaranteed to be called before any use of this instance for
     * validation.
     * <p>
     * The default implementation is a no-op.
     *
     * @param constraintAnnotation annotation instance for a given constraint declaration
     */
    @Override
    public void initialize(PasswordNonReusePolicyValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        if (constraintAnnotation.nonReusePassword() != null) {
            this.nonReusePassword = constraintAnnotation.nonReusePassword();
        } else {
            this.nonReusePassword = getCurrentUserPassword();
        }

        this.message = constraintAnnotation.message();
    }

    private String getCurrentUserPassword() {
        String currentUserPassword = userDetailsService.loadUserByUsername(principal.getName()).getPassword();
        if (currentUserPassword == null) {
            System.out.println("currentUserPassword = userDetailsService.loadUserByUsername(principal.getName()).getPassword();" + currentUserPassword);
            currentUserPassword = NOTYET_OR_NULL_nonReusePassword;
            return currentUserPassword;
        } else {
            System.out.println(" return \"userDetailsService.loadUserByUsername(principal.getName()).getPassword();" + currentUserPassword);
            return currentUserPassword;
        }

    }
}
