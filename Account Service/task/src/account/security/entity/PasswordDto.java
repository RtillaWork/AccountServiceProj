package account.security.entity;

import account.entity.PersonDto;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraintvalidation.SupportedValidationTarget;

@Entity
@Service
public class PasswordDto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

//    @JsonIgnore
//    @NotNull
//    private static boolean isCurrent = true;  // future use, onetoMany user-> passwords

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    private PersonDto user;

//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)


    @JsonIgnore
//    @NotEmpty
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 12, message = "The password length must be at least 12 chars!")
    @Transient
    @JsonAlias("new_password")
    private String transientPassword;


//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    @NotEmpty
//    @Size(min = 12)
//    @Transient
//    private String new_password;

    public PasswordDto() {
    }

    @Valid
    public PasswordDto(@Valid String transientPassword) {
        this.setTransientPassword(transientPassword);
        this.setPassword(transientPassword);
    }

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }

    //    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonIgnore
    public void setPassword(@Valid
                                    String transientPassword) {
        this.password = this.passwordEncoderImpl().encode(transientPassword);
    }

    public String getTransientPassword() {
        return this.transientPassword;
    }
//
    public void setTransientPassword( String transientPassword) {
        this.transientPassword = transientPassword;
    }

//    public boolean isValid() {
//        // TODO
//        return true;
//    }

//    setPassword(passwordEncoder.encode(this.getPassword()));

    @Bean
    public PasswordEncoder passwordEncoderImpl() {
        return new BCryptPasswordEncoder();
//        return NoOpPasswordEncoder.getInstance();
    }
}
