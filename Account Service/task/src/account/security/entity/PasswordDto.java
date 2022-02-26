package account.security.entity;

import account.entity.PersonDto;
import account.entity.validation.PasswordLengthValidation;
import account.security.PasswordEncoderImpl;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.Random;

@Entity
//@Validated
public class PasswordDto {

    // TODO DELETEME AFTER TESTING
    private static final String DEFAULT_CLEARTEXT_PASSWORD = "DEBUG_PASSWORD123456789012";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

//    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    private PersonDto user;

    //    @JsonIgnore
    private String hashedPassword;

    // starts with a
    private boolean isHashedPasswordReady;

    @PasswordLengthValidation
    @Transient
    private String clearTextPassword;


    public PasswordDto() {
        setHashedPasswordRandomOrDefault(false);
    }

    public PasswordDto(@Valid  @PasswordLengthValidation String clearTextPassword) {
        this.setHashedPassword(clearTextPassword);
    }

    public Long getId() {
        return id;
    }

    //    @JsonIgnore
    @JsonProperty(value = "DEBUGdtohashedpassword", access = JsonProperty.Access.READ_ONLY)
    public String getHashedPassword() {
        return this.hashedPassword;
    }

    @JsonIgnore
    public void setHashedPassword(@Valid String clearTextPassword) {
        PasswordEncoderImpl passwordEncoder = new PasswordEncoderImpl();
        this.hashedPassword = passwordEncoder.passwordEncoder().encode(clearTextPassword);
        setIsHashedPasswordReady(true);
    }

    @JsonProperty(value = "password", access = JsonProperty.Access.READ_WRITE)//, access = JsonProperty.Access.WRITE_ONLY)
    @JsonAlias("new_password")
    public String getCleartextPassword() {
        return this.clearTextPassword;
    }

    @JsonProperty(value = "password", access = JsonProperty.Access.READ_WRITE)//, access = JsonProperty.Access.WRITE_ONLY)
    @JsonAlias("new_password")
    public void setClearTextPassword(@Valid     @PasswordLengthValidation
                                                 String clearTextPassword) {
        this.clearTextPassword = clearTextPassword;
        setHashedPassword(clearTextPassword);
    }

    // Utility methods

//    @JsonIgnore
@JsonProperty(access = JsonProperty.Access.READ_ONLY)
public PersonDto getUser() {
        return user;
    }

    @JsonIgnore
    public void setUser(PersonDto user) {
        this.user = user;
    }

//    @JsonIgnore
@JsonProperty(access = JsonProperty.Access.READ_ONLY)
public boolean isIsHashedPasswordReady() {
        return isHashedPasswordReady;
    }

    @JsonIgnore
    public void setIsHashedPasswordReady(boolean hashedPasswordComputed) {
        this.isHashedPasswordReady = hashedPasswordComputed;
    }

    @JsonIgnore
    public void setHashedPasswordRandomOrDefault(boolean isRandom) {
        isRandom = false; // TODO DELETEME AFTER TESTING
        if (isRandom) {} else {
            PasswordEncoderImpl passwordEncoder = new PasswordEncoderImpl();
            this.hashedPassword = passwordEncoder.passwordEncoder().encode(DEFAULT_CLEARTEXT_PASSWORD);
            setIsHashedPasswordReady(false);
        }
    }


}
