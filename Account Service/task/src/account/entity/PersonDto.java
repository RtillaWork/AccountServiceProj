package account.entity;

import account.route.Api;
import account.security.EmployeeGrantedAuthorityImpl;
import account.security.entity.UserDetailsDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
public class PersonDto extends UserDetailsDto {

    private static final String
            EMAIL_REGEXP = "@" + Api.VALID_DOMAINS.get("Corporate") + "$";


    @NotEmpty
    private String name;

    @NotEmpty
    private String lastname;

    @NotEmpty
    @Column(unique = true)
    @Email(regexp = "^(.+)@acme.com$", message = "email must be valid")
    private String email;

    public PersonDto() {
       setAuthorities(Set.of(new EmployeeGrantedAuthorityImpl()));
    }

//    public PersonDto(String name, String lastname, String email, String password) {
//        super(email, password);
//        this.name = name;
//        this.lastname = lastname;
//        this.email = email;
//    }
//
//    public PersonDto(String username, String password, String name, String lastname, String email) {
//        super(username, password);
//        this.name = name;
//        this.lastname = lastname;
//        this.email = email;
//    }

    @JsonProperty(value="name")
    public String getName() {
        return name;
    }

    @JsonProperty(value="name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty(value="lastname")
    public String getLastname() {
        return lastname;
    }

    @JsonProperty(value="lastname")
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @JsonProperty(value="email")
    public String getEmail() {
        return email;
    }

    @JsonProperty(value="email")
    public void setEmail(String email) {
        this.username = email;
        this.email = email;
    }


    public void make(@Autowired GrantedAuthority grantedAuthority) {
        setAuthorities(Set.of(grantedAuthority));
        // NOTE .email is case-insensitive
        // NOTE .email will never be null because of validation rules
        // NOTE run normalized(...) only one, by checking if .id has already been set != null

        if (this.getEmail() != null) {
            setEmail(this.getEmail().toLowerCase());
        }

//        if (this.getPassword() != null) {
//            setPassword(this.getPassword());
//        }
//
//
//        if (this.getId() != null) {
//            setPassword(this.getPassword());
//        }
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
