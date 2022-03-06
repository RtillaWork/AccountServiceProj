package account.entity.validation;

import account.exception.PasswordRequirementException;
import account.exception.password.PasswordLengthValidationException;

import javax.validation.ConstraintValidator;
        import javax.validation.ConstraintValidatorContext;
        import javax.validation.Validation;

public class PasswordLengthValidator implements ConstraintValidator<PasswordLengthValidation, String> {
    private static int min;
    private static int max;
    private static String message;

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
        if (value == null) {
            // TODO: should this throw a ConstraintValidationException?
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();

            System.err.println("FROM PasswordLengthValidator DEBUG ISVALID VALUE IS NULL = " + value);

            return false;
        } else  if (value.length() < min || value.length() > max) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();

            System.err.println("FROM PasswordLengthValidator DEBUG ISVALID VALUE throw new PasswordLengthValidationException(); = " + value);

            return false;
        }
        else {
            return true;
        }
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
    public void initialize(PasswordLengthValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        min = constraintAnnotation.min();
        max = constraintAnnotation.max();
        message = constraintAnnotation.message();
    }
}