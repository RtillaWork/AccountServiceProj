package account.security.entity;

import account.entity.PersonDto;
import account.entity.validation.PasswordLengthValidation;
import account.entity.validation.PasswordPolicyValidation;
import account.security.PasswordEncoderImpl;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Validated
public class PasswordDto {

    // TODO DELETEME AFTER TESTING
    private static final String DEFAULT_CLEARTEXT_PASSWORD = "DEBUG_PASSWORD123456789012";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonIgnore
    @OneToOne(mappedBy = "passwordDto")
    private PersonDto user;

    @JsonIgnore
    private String hashedPassword;

    // starts with a
    @Transient
    private boolean hashedPasswordReady;

    @Transient
    @JsonAlias("new_password")
    private String clearTextPassword;


    public PasswordDto() {
//        this.clearTextPassword = DEFAULT_CLEARTEXT_PASSWORD;
//        setHashedPasswordRandomOrDefault(false);
    }

    public PasswordDto(@Valid String cleartextTransientPassword) {
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
    @Validated
    public void setHashedPassword(@Valid @PasswordPolicyValidation @PasswordLengthValidation String cleartextTransientPassword) {
        PasswordEncoderImpl passwordEncoder = new PasswordEncoderImpl();
//        this.hashedPassword = passwordEncoder.passwordEncoder().encode(clearTextPassword);
//        this.hashedPassword = NoOpPasswordEncoder.getInstance().encode(cleartextTransientPassword);
        this.hashedPassword =passwordEncoder.passwordEncoder().encode(cleartextTransientPassword);

        setHashedPasswordReady(true);
        System.out.println(" setIsHashedPasswordReady(true): " + clearTextPassword + " hashed: " + this.hashedPassword);
//        setClearTextPassword(null);
//        System.out.println("setClearTextPassword(null);");

    }

    @JsonProperty(value = "Cleartextpassword", access = JsonProperty.Access.READ_WRITE)
//, access = JsonProperty.Access.WRITE_ONLY)
    @JsonAlias("new_password")
    public String getClearTextPassword() {
        return this.clearTextPassword;
    }

    @JsonProperty(value = "Cleartextpassword", access = JsonProperty.Access.READ_WRITE)
//, access = JsonProperty.Access.WRITE_ONLY)
    @JsonAlias("new_password")
    public void setClearTextPassword(@PasswordPolicyValidation @PasswordLengthValidation(message = "The password length must be at least 12 chars! passdto")

                                             String clearTextPassword) {
        this.clearTextPassword = clearTextPassword;
    }


    // Utility methods

    @JsonIgnore
//@JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public PersonDto getUser() {
        return user;
    }

    @JsonIgnore
    public void setUser(PersonDto user) {
        this.user = user;
    }

    //    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public boolean isHashedPasswordReady() {
        return hashedPasswordReady;
    }

    @JsonIgnore
    public void setHashedPasswordReady(boolean hashedPasswordComputed) {
        this.hashedPasswordReady = hashedPasswordComputed;
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
