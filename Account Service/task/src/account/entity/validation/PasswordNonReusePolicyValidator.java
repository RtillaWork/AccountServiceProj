package account.entity.validation;

import account.security.config.PasswordEncoderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.util.Optional;


public class PasswordNonReusePolicyValidator implements ConstraintValidator<PasswordNonReusePolicyValidation, String> {

    // this value should be the most random and represent an absence of prior passwords in DB, on initial User creation for ex.
    private static final String NONEYET_OR_NULL_nonReusePassword = "DEBUG_NONEYET_OR_NULL_INITIAL_USER_CREATION__PASSWORD";

//    @Autowired
//    PasswordEncoder passwordEncoder;

    @Autowired
    UserDetailsService userDetailsService;

//    Authentication auth;

////    Principal principal = getPrincipal(getPrincipal());
//
//    //    @Inject private Principal principal;
//    private Principal principal;

    private static Optional<String> currentUserPassword = Optional.empty(); // = NOTYET_OR_NULL_nonReusePassword;
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
        PasswordEncoderImpl passwordEncoderImpl = new PasswordEncoderImpl();

        boolean isValid = true;
        Optional<String> nonReusePassword = currentUserPassword; // getCurrentUserPassword();

//        if (value == null) {
//            // TODO: should this throw a ConstraintValidationException?
//            context.disableDefaultConstraintViolation();
//            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
//            System.out.println("DEBUG ISVALID VALUE IS NULL = " + value);
//            isValid = true;// TODO the case of null password should be handled by a separate annotation, so ignore null buy returning true
//
//        } else if (nonReusePassword.isEmpty()) {
//            // TODO the case of null password should be handled by a separate annotation, so ignore null buy returning true
//            // TODO WIP almost same as above, make valid to ignore annotation
//            isValid = true;
//        } else {
//            // TODO Warning providing a nonReusePassword shortcircuits the last else check against DB prev password(s).
//            int isPasswordReused = (value.equals(nonReusePassword.get()) ? 1 : 0); // passwordEncoderImpl.passwordEncoder().matches(value, nonReusePassword.get()) ? 1 : 0;
//            System.out.println(" DEBUG VALUE: " + value + " matches(value, nonReusePassword): " + isPasswordReused);
//            switch (isPasswordReused) {
//                case 1: {
//                    context.disableDefaultConstraintViolation();
//                    context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
//                    System.out.println("DEBUG ISVALID VALUE throw new PasswordNonReusePolicyValidation(); = " + value + " nonresuepass: " + nonReusePassword.get());
//                    isValid = false;
//                    break;
//                }
//                case 0: {
//                    isValid = true;
//                    System.out.println("DEBUG ISVALID: " + isValid + " VALUE =" + value + " nonresuepass: " + nonReusePassword.get());
//                    break;
//                }
//            }
//
//        }
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
        setOnceCurrentUserPassword();
        this.message = constraintAnnotation.message();

//        if (nonReusePassword == null) {
//            nonReusePassword = getCurrentUserPassword().orElse(NONEYET_OR_NULL_nonReusePassword);
//            this.message = constraintAnnotation.message();
//
//        } else if ( NONEYET_OR_NULL_nonReusePassword.equals(nonReusePassword)){
//            this.message = "THIS MESSAGE + " +  NONEYET_OR_NULL_nonReusePassword;
//        } else {
//            this.message = "THIS MESSAGE = UNKNOWN STATE NEITHER NULL NOR INITIALIZED";
//
//        }
//
//        System.out.println("!constraintAnnotation.nonReusePassword().isBlank() " + !constraintAnnotation.nonReusePassword().isBlank());
////
////        if (!constraintAnnotation.nonReusePassword().isBlank()) {
////            System.out.println("!constraintAnnotation.nonReusePassword().isBlank()");
////            this.nonReusePassword = constraintAnnotation.nonReusePassword();
////        } else if (principal != null) {
////            System.out.println("principal != null");
////            this.nonReusePassword = getCurrentUserPassword();
////        } else {
////            System.out.println("this.nonReusePassword = NOTYET_OR_NULL_nonReusePassword;");
////            this.nonReusePassword = NOTYET_OR_NULL_nonReusePassword;
////        }
//
    }

    private void setOnceCurrentUserPassword() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (currentUserPassword.isPresent() || auth == null || auth instanceof AnonymousAuthenticationToken) {
            return;
        } else if (userDetailsService == null) {
            return;
        }else {
            UserDetails userDetails = userDetailsService.loadUserByUsername(auth.getName());
            currentUserPassword = Optional.of(userDetails.getPassword());
                        System.out.println("AUTHENTICATION IS: " + auth.getPrincipal());
            System.out.println(" UserDetails userDetails = userDetailsService. :" + userDetailsService.getClass());
            System.out.println(" UserDetails userDetails = auth.getName(). :" + auth.getName());
            System.out.println(" UserDetails userDetails = userDetailsService.loadUserByUsername(auth.getName()). :" + userDetailsService.loadUserByUsername(auth.getName()));
            System.out.println(" UserDetails userDetails = getPassword. :" + userDetails.getPassword());

        }
    }


    private Optional<String> getCurrentUserPassword() {
//        UserDetailsService userDetailsService = new UserDetailsServiceImpl();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }
//        else if (userDetailsService == null) {
//            return;
//        }
        else {

//            System.out.println("AUTHENTICATION IS: " + auth.getPrincipal());
//            System.out.println(" UserDetails userDetails = userDetailsService. :" + userDetailsService.getClass());
//            System.out.println(" UserDetails userDetails = auth.getName(). :" + auth.getName());
//            System.out.println(" UserDetails userDetails = userDetailsService.loadUserByUsername(auth.getName()). :" + userDetailsService.loadUserByUsername(auth.getName()));

            UserDetails userDetails = userDetailsService.loadUserByUsername(auth.getName());
            String currentUserPassword = userDetails.getPassword();

                    System.out.println(" currentUserPassword = userDetails.getPassword(); :" + currentUserPassword);
            System.out.println("Principal principal = (Principal) auth.getPrincipal();: " + currentUserPassword);

            return Optional.of(currentUserPassword);
        }
    }

//    void getPrincipal(@AuthenticationPrincipal Principal principal) {
//        System.out.println("DEBUG getPrincipal(@AuthenticationPrincipal Principal principal): " + principal.toString());
//        return principal;
//    }
}
