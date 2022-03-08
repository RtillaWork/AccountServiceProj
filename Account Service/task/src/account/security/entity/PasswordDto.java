package account.security.entity;

import account.entity.EmployeeDto;
import account.entity.validation.PasswordLengthValidation;
import account.entity.validation.PasswordNonReusePolicyValidation;
import account.entity.validation.PasswordPolicyValidation;
import account.exception.password.PasswordReuseException;
import account.security.config.PasswordEncoderImpl;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.Validator;

@Entity
@Validated
@Valid
//@PasswordNonReusePolicyValidation // (TODO NOTE: only relevant if (User, Password(s)) already created
public class PasswordDto {

//    @Autowired
//    Validator validator;

    // TODO DELETEME AFTER TESTING
    private static final String DEFAULT_CLEARTEXT_PASSWORD = "DEBUG_PASSWORD123456789012";
    private static final String EXPIRED_OR_DEFAULT_HASHED_PASSWORD = "N/A"; // should be random hash for added security

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonIgnore
    @OneToOne(mappedBy = "passwordDto", fetch = FetchType.EAGER)
    private EmployeeDto userDto; // TODO NOTE or any implementation of UserDetails interface

    @JsonIgnore
//    @ColumnDefault("varchar(255) default '" + DEFAULT_HASHED_PASSWORD + "'")
    private String hashedPassword = EXPIRED_OR_DEFAULT_HASHED_PASSWORD;

    // starts with a
    @Transient
    private boolean hashedPasswordReady;


    //    @JsonAlias("new_password")
    @Transient
    @PasswordPolicyValidation
    @PasswordLengthValidation
    private String cleartextNewPassword;

    @Transient
    private String hashedNewPassword;


    public PasswordDto() {
        if ( this.userDto != null && this.getHashedPassword().equals(EXPIRED_OR_DEFAULT_HASHED_PASSWORD)) {
            this.userDto.setCredentialstNonExpired(false);
        }
//        this.clearTextPassword = DEFAULT_CLEARTEXT_PASSWORD;
//        setHashedPasswordRandomOrDefault(false);

//        initHashedPasswordNotReady();//NOTE MUST BE DEACTIVATED TO ALLOW ACCESS TO STORED HASHEDPASSWORD BEFORE
        // ANYTHING ELSE INCLUDING VALIDATION
    }

    public PasswordDto(
//            @Valid
//                       @PasswordPolicyValidation
//                       @PasswordLengthValidation
//                       @PasswordNonReusePolicyValidation
            String cleartextTransientPassword) {
        this();
        this.setHashedPassword(cleartextTransientPassword);
    }

    public Long getId() {
        return id;
    }

            @JsonIgnore
//    @JsonProperty(value = "DEBUGdtohashedpassword", access = JsonProperty.Access.READ_ONLY)
    public String getHashedPassword() {
        // must add exception check around userDto credentialsNonExpired throw on false
        return this.hashedPassword;
    }


    @JsonIgnore
    @Validated
    public void setHashedPassword() {
            PasswordEncoderImpl passwordEncoder = new PasswordEncoderImpl();
            this.hashedPassword = passwordEncoder.passwordEncoder().encode(this.getCleartextNewPassword());
//        this.hashedPassword = NoOpPasswordEncoder.getInstance().encode(this.getClearTextPassword());

        //old
//        if (isHashedPasswordNotReady()) {
//            PasswordEncoderImpl passwordEncoder = new PasswordEncoderImpl();
//            this.hashedPassword = passwordEncoder.passwordEncoder().encode(this.getCleartextNewPassword());
////        this.hashedPassword = NoOpPasswordEncoder.getInstance().encode(this.getClearTextPassword());
//            setHashedPasswordReady(true);
//            obfuscateClearTextPassword();
//        }
    }

    @JsonIgnore
//    @Validated
    public void setHashedPassword(
//            @Valid
//            @PasswordPolicyValidation
//            @PasswordLengthValidation
//            @PasswordNonReusePolicyValidation
            String cleartextTransientPassword) {

        PasswordEncoderImpl passwordEncoder = new PasswordEncoderImpl();


        if (passwordEncoder.passwordEncoder().matches(cleartextTransientPassword, this.getHashedPassword())) {
            throw new PasswordReuseException();
        } else {
            this.hashedPassword = passwordEncoder.passwordEncoder().encode(cleartextTransientPassword);
        }

        // older
//        if (isHashedPasswordNotReady()) {
//            this.cleartextNewPassword = cleartextTransientPassword;
//            this.setHashedPassword();
//            setHashedPasswordReady(true);
//            obfuscateClearTextPassword();
//        }
    }


    //    @JsonProperty(value = "cleartextpassword", access = JsonProperty.Access.READ_WRITE)
//, access = JsonProperty.Access.WRITE_ONLY)
    @JsonAlias("new_password")
    public String getCleartextNewPassword() {
        return this.cleartextNewPassword;

//        if (isHashedPasswordNotReady()) {
//            return this.cleartextNewPassword;
//        } else {
//            throw new RuntimeException("DEBUG FROM PasswordDto: call to getClearText while hashedPassword true: already read. PLease provide a new clearTextPassword");
//        }
    }

    //    @JsonProperty(value = "Cleartextpassword", access = JsonProperty.Access.READ_WRITE)
    @JsonProperty(value = "new_password", access = JsonProperty.Access.WRITE_ONLY)
    @JsonAlias("new_password")
    public void setCleartextNewPassword(
//            @Valid
//                                     @PasswordPolicyValidation
//                                     @PasswordLengthValidation
//                                     @PasswordNonReusePolicyValidation
            String cleartextNewPassword) {
        this.cleartextNewPassword = cleartextNewPassword;
//        setHashedPasswordReady(false);
//        this.setHashedPassword(cleartextNewPassword);
//        obfuscateClearTextPassword();
    }

    //    @JsonProperty(value = "Cleartextpassword", access = JsonProperty.Access.READ_WRITE)
    @JsonProperty(value = "new_password", access = JsonProperty.Access.WRITE_ONLY)
    public void setPassword(
//            @Valid
//                            @PasswordPolicyValidation
//                            @PasswordLengthValidation
//                            @PasswordNonReusePolicyValidation
                                    String clearTextPassword) {
        setCleartextNewPassword(clearTextPassword);
        //        setHashedPasswordReady(false);
        this.setHashedPassword(getCleartextNewPassword());
//        obfuscateClearTextPassword();
    }

    //    @JsonProperty(value = "Cleartextpassword", access = JsonProperty.Access.READ_WRITE)
//    @JsonProperty(value = "new_password", access = JsonProperty.Access.WRITE_ONLY)
//    @JsonAlias("new_password")
    public void updatePassword(
//            @Valid
//                               @PasswordPolicyValidation
//                               @PasswordLengthValidation
//                               @PasswordNonReusePolicyValidation
            String clearTextPassword) {
//        validator.validate()
        this.setCleartextNewPassword(clearTextPassword);
    }

    public void updatePassword(PasswordDto newPasswordDto) {
         updatePassword( newPasswordDto.getCleartextNewPassword());
    }


    public void obfuscateClearTextPassword() {
        // TODO deactivate for now as it interferes with @PasswordNonReusePolicyValidation which needs an intact cleartextPassword
        // expecting @Transient to provide some security, although cleartextPassword will still be hanging around in RAM
//        this.cleartextNewPassword = this.getHashedPassword();
    }

    ///////////////////////////////////////////////
    // Utility methods
    ///////////////////////////////////////////////

    @JsonIgnore
//@JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public EmployeeDto getUserDto() {
        return userDto;
    }

    @JsonIgnore
    public void setUserDto(UserDto user) {
        this.userDto = (EmployeeDto) user;
    }

    public boolean isUserDtoSet() {
        return this.userDto != null;
    }

    //    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public boolean isHashedPasswordReady() {
        return hashedPasswordReady;
    }

    //    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public boolean isHashedPasswordNotReady() {
        return !isHashedPasswordReady();
    }

    @JsonIgnore
    public void setHashedPasswordReady(boolean hashedPasswordComputed) {
        this.hashedPasswordReady = hashedPasswordComputed;
    }

    @JsonIgnore
    public void initHashedPasswordNotReady() {
        this.hashedPasswordReady = false;
    }


    @JsonIgnore
    public void setHashedPasswordRandomOrDefault(boolean isRandom) {
        isRandom = false; // TODO DELETEME AFTER TESTING
        if (isRandom) {
        } else {
            PasswordEncoderImpl passwordEncoder = new PasswordEncoderImpl();
            this.hashedPassword = passwordEncoder.passwordEncoder().encode(DEFAULT_CLEARTEXT_PASSWORD);
            this.hashedPassword = "HASHED"; //TODO DEBUG DELETEME
            setHashedPasswordReady(false);
        }
    }
}
