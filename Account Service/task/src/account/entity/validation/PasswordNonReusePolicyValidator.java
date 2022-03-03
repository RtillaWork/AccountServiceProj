package account.entity.validation;

import account.security.PasswordEncoderImpl;
import account.security.entity.PasswordDto;
import account.service.PasswordRepositoryService;
import account.service.PersonRepositoryService;
import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.security.Principal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


public class PasswordNonReusePolicyValidator implements ConstraintValidator<PasswordNonReusePolicyValidation, String> {

    @Autowired
    PasswordEncoderImpl passwordEncoder;

    @Autowired
    UserDetailsService userDetailsService;

    //    @Inject private Principal principal;
    private Principal principal;

    private String nonReusePassword;
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
            isValid = false;
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
                    isValid = false;
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
        return userDetailsService.loadUserByUsername(principal.getName()).getPassword();
    }
}
