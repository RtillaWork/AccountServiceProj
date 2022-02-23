package account.entity;

import account.route.Api;
import account.security.EmployeeGrantedAuthorityImpl;
import account.security.entity.UserDetailsImplDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
public class PersonDTO extends UserDetailsImplDTO {

    private static final String
            EMAIL_REGEXP = "@" + Api.VALID_DOMAINS.get("Corporate") + "$";


    @NotEmpty
    @JsonProperty
    private String name;

    @NotEmpty
    @JsonProperty
    private String lastname;

    @NotEmpty
    @Column(unique = true)
    @Email(regexp = "^(.+)@acme.com$", message = "email must be valid")
    @JsonProperty
    private String email;

    public PersonDTO() {
       setAuthorities(Set.of(new EmployeeGrantedAuthorityImpl()));
    }

    public PersonDTO(String name, String lastname, String email, String password) {
        super(email, password);
        this.name = name;
        this.lastname = lastname;
        this.email = email;
    }

    public PersonDTO(String username, String password, String name, String lastname, String email) {
        super(username, password);
        this.name = name;
        this.lastname = lastname;
        this.email = email;
    }

//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getLastname() {
//        return lastname;
//    }
//
//    public void setLastname(String lastname) {
//        this.lastname = lastname;
//    }

    public String getEmail() {
        return email;
    }

    @JsonProperty
    public void setEmail(String email) {
        this.username = email;
        this.email = email;
    }

//    public void normalized() {
//        // NOTE .email is case-insensitive
//        // NOTE .email will never be null because of validation rules
//        setEmail(this.getEmail().toLowerCase());
//    }

//    public void init(PasswordEncoder passwordEncoder) {
//        // NOTE .email is case-insensitive
//        // NOTE .email will never be null because of validation rules
//        // NOTE run normalized(...) only one, by checking if .id has already been set != null
//        if (this.getId() != null) {
//            setEmail(this.getEmail().toLowerCase());
//            setPassword(passwordEncoder.encode(this.getPassword()));
//        }
//    }
//
//    public void init(PasswordEncoder passwordEncoder, GrantedAuthority grantedAuthority) {
//        setAuthorities(Set.of(grantedAuthority));
//        // NOTE .email is case-insensitive
//        // NOTE .email will never be null because of validation rules
//        // NOTE run normalized(...) only one, by checking if .id has already been set != null
//        if (this.getId() != null) {
//            setEmail(this.getEmail().toLowerCase());
//            setPassword(passwordEncoder.encode(this.getPassword()));
//        }
//    }

//    public void build(GrantedAuthority grantedAuthority) {
//        setAuthorities(Set.of(grantedAuthority));
//        // NOTE .email is case-insensitive
//        // NOTE .email will never be null because of validation rules
//        // NOTE run normalized(...) only one, by checking if .id has already been set != null
//        if (this.getPassword() != null) {
//            setPassword(this.getPassword());
//        }
//
//        if (this.getEmail() != null) {
//            setEmail(this.getEmail().toLowerCase());
//        }
////        if (this.getId() != null) {
////            setPassword(this.getPassword());
////        }
//    }



}
