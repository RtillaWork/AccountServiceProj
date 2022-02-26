package account.security.entity;

import account.entity.PersonDto;
import account.entity.validation.PasswordLengthValidation;
import account.security.PasswordEncoderImpl;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Entity
public class PasswordDto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    private PersonDto user;

    //    @JsonIgnore
    @NotEmpty
    private String hashedPassword;

    @PasswordLengthValidation
    @Transient
    private String clearTextPassword;


    public PasswordDto() {
//        create random password
    }

    @Valid
    public PasswordDto(@Valid String clearTextPassword) {
        this.setHashedPassword(clearTextPassword);
    }

    public Long getId() {
        return id;
    }

    //    @JsonIgnore
    public String getHashedPassword() {
        return hashedPassword;
    }

    @JsonIgnore
    public void setHashedPassword(@Valid String clearTextPassword) {
        PasswordEncoderImpl passwordEncoder = new PasswordEncoderImpl();
        this.hashedPassword = passwordEncoder.passwordEncoder().encode(clearTextPassword);
    }

    @JsonProperty(value = "new_password")//, access = JsonProperty.Access.WRITE_ONLY)
    public String getCleartextPassword() {
        return this.clearTextPassword;
    }

    @JsonProperty(value = "new_password")//, access = JsonProperty.Access.WRITE_ONLY)
    public void setClearTextPassword(@Valid String clearTextPassword) {
        this.clearTextPassword = clearTextPassword;
    }



}
