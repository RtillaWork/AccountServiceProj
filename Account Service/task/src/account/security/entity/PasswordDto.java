package account.security.entity;

import account.entity.EmployeeDto;
import account.entity.validation.PasswordLengthValidation;
import account.entity.validation.PasswordNonReusePolicyValidation;
import account.entity.validation.PasswordPolicyValidation;
import account.security.config.PasswordEncoderImpl;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Validated
public class PasswordDto {

    // TODO DELETEME AFTER TESTING
    private static final String DEFAULT_CLEARTEXT_PASSWORD = "DEBUG_PASSWORD123456789012";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonIgnore
    @OneToOne(mappedBy = "passwordDto", fetch = FetchType.EAGER)
    private EmployeeDto userDto;

    @JsonIgnore
    private String hashedPassword;

    // starts with a
    @Transient
    private boolean hashedPasswordReady;

    @Transient
    @PasswordPolicyValidation
    @PasswordLengthValidation
    @PasswordNonReusePolicyValidation // (TODO NOTE: only relevant if (User, Password(s)) already created
    @JsonAlias("new_password")
    private String clearTextPassword;


    public PasswordDto() {
//        this.clearTextPassword = DEFAULT_CLEARTEXT_PASSWORD;
//        setHashedPasswordRandomOrDefault(false);
        initHashedPasswordNotReady();
    }

    public PasswordDto(@Valid
                       @PasswordPolicyValidation
                       @PasswordLengthValidation
                       @PasswordNonReusePolicyValidation
                               String cleartextTransientPassword) {
        this.setHashedPassword(cleartextTransientPassword);
    }

    public Long getId() {
        return id;
    }

    //        @JsonIgnore
    @JsonProperty(value = "DEBUGdtohashedpassword", access = JsonProperty.Access.READ_ONLY)
    public String getHashedPassword() {
        return this.hashedPassword;
    }

    @JsonIgnore
//    @Validated
    public void setHashedPassword(
            @Valid
            @PasswordPolicyValidation
            @PasswordLengthValidation
            @PasswordNonReusePolicyValidation
                    String cleartextTransientPassword) {
//        this.hashedPassword = NoOpPasswordEncoder.getInstance().encode(cleartextTransientPassword);
//        this.hashedPassword = passwordEncoder.passwordEncoder().encode(cleartextTransientPassword);
        if (isHashedPasswordNotReady()) {
            this.clearTextPassword = cleartextTransientPassword;
            this.setHashedPassword();
            setHashedPasswordReady(true);
            obfuscateClearTextPassword();
        }
    }

    @JsonIgnore
    @Validated
    public void setHashedPassword() {
        if (isHashedPasswordNotReady()) {
            PasswordEncoderImpl passwordEncoder = new PasswordEncoderImpl();
            this.hashedPassword = passwordEncoder.passwordEncoder().encode(this.getClearTextPassword());
//        this.hashedPassword = NoOpPasswordEncoder.getInstance().encode(this.getClearTextPassword());
            setHashedPasswordReady(true);
            obfuscateClearTextPassword();
        }
    }

    //    @JsonProperty(value = "cleartextpassword", access = JsonProperty.Access.READ_WRITE)
//, access = JsonProperty.Access.WRITE_ONLY)
//    @JsonAlias("new_password")
    public String getClearTextPassword() {
        if (isHashedPasswordNotReady()) {
            return this.clearTextPassword;
        } else {
            throw new RuntimeException("DEBUG FROM PasswordDto: call to getClearText while hashedPassword true: already read. PLease provide a new clearTextPassword");
        }
    }

    //    @JsonProperty(value = "Cleartextpassword", access = JsonProperty.Access.READ_WRITE)
    @JsonProperty(value = "new_password", access = JsonProperty.Access.WRITE_ONLY)
    @JsonAlias("new_password")
    public void setClearTextPassword(@Valid
                                     @PasswordPolicyValidation
                                     @PasswordLengthValidation
                                     @PasswordNonReusePolicyValidation
                                             String clearTextPassword) {
        this.clearTextPassword = clearTextPassword;
        setHashedPasswordReady(false);
        this.setHashedPassword(clearTextPassword);
        obfuscateClearTextPassword();
    }

    //    @JsonProperty(value = "Cleartextpassword", access = JsonProperty.Access.READ_WRITE)
    @JsonProperty(value = "new_password", access = JsonProperty.Access.WRITE_ONLY)
    @JsonAlias("new_password")
    public void setPassword(@Valid
                            @PasswordPolicyValidation
                            @PasswordLengthValidation
                            @PasswordNonReusePolicyValidation
                                    String clearTextPassword) {
        setClearTextPassword(clearTextPassword);
    }

    //    @JsonProperty(value = "Cleartextpassword", access = JsonProperty.Access.READ_WRITE)
    @JsonProperty(value = "new_password", access = JsonProperty.Access.WRITE_ONLY)
    @JsonAlias("new_password")
    public void updatePassword(@Valid
                               @PasswordPolicyValidation
                               @PasswordLengthValidation
                               @PasswordNonReusePolicyValidation
                                       String clearTextPassword) {
        this.setClearTextPassword(clearTextPassword);
    }

    public void obfuscateClearTextPassword() {
        this.clearTextPassword = this.getHashedPassword();
    }

    // Utility methods

    @JsonIgnore
//@JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public EmployeeDto getUserDto() {
        return userDto;
    }

    @JsonIgnore
    public void setUserDto(UserDto user) {
        this.userDto = (EmployeeDto) user;
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
