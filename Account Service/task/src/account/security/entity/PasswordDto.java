package account.security.entity;

import account.entity.PersonDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

@Entity
@Service
public class PasswordDto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonIgnore
    @NotNull
    private boolean isCurrent;

    @JsonIgnore
    @ManyToOne
    private PersonDto user;

//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)

    @NotEmpty
    private String password;

//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    @NotEmpty
//    @Size(min = 12)
//    @Transient
//    private String new_password;

    @Valid
    public PasswordDto() {
    }

    @Valid
    public PasswordDto(@Validated @NotEmpty(message = "The password cannot be empty")
                       @Size(min = 12, message = "The password length must be at leat 12 chars!")
                               String transientPassword) {
        this.password = this.passwordEncoderImpl().encode(transientPassword);
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

    public void setPassword(@Validated @NotEmpty(message = "The password cannot be empty")
                            @Size(min = 12, message = "The password length must be at leat 12 chars!")
                                    String transientPassword) {
        this.password = this.passwordEncoderImpl().encode(transientPassword);
    }

//    public String getNew_password() {
//        return new_password;
//    }
//
//    public void setNew_password(String new_password) {
//        this.new_password = new_password;
//    }

    public boolean isValid() {
        // TODO
        return true;
    }

//    setPassword(passwordEncoder.encode(this.getPassword()));

    @Bean
    public PasswordEncoder passwordEncoderImpl() {

        return new BCryptPasswordEncoder();
//        return NoOpPasswordEncoder.getInstance();
    }
}
