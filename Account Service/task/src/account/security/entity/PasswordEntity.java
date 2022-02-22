package account.security.entity;

import account.entity.Person;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Service
public class PasswordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotEmpty
    @OneToOne(fetch = FetchType.EAGER, optional = false, orphanRemoval = false, cascade = CascadeType.ALL)
    private Person person;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty
    @Size(min = 12)
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty
    @Size(min = 12)
    @Transient
    private String new_password;

    public PasswordEntity() { }

    public PasswordEntity(String password) {
        this.password = this.passwordEncoderImpl().encode(password);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

    public boolean isValid() {
        // TODO
        return true;
    }

//    setPassword(passwordEncoder.encode(this.getPassword()));

    @Bean
    public PasswordEncoder passwordEncoderImpl() {
        return new BCryptPasswordEncoder();
    }
}
