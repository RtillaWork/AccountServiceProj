package account.entity;

import account.entity.validation.PasswordLengthValidation;
import account.entity.validation.PasswordNonReusePolicyValidation;
import account.entity.validation.PasswordPolicyValidation;
import account.exception.PasswordRequirementException;
import account.route.Api;
import account.security.entity.PasswordDto;
import account.security.entity.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Entity
public class EmployeeDto extends UserDto {

    private static final String
            EMAIL_REGEXP = "@" + Api.VALID_DOMAINS.get("Corporate") + "$";

    @NotBlank
    private String name;

    @NotBlank
    private String lastname;

    @NotBlank
    @Column(unique = true)
    @Email(regexp = "^(.+)@acme.com$", message = "email must be valid")
    private String email;

    @Transient
    @PasswordPolicyValidation
    @PasswordLengthValidation(message = "Password length must be 12 chars minimum userDto!")
//    @PasswordNonReusePolicyValidation // (TODO NOTE irrelevant as the User created first time with its password)
    private String cleartextTransientPassword;

    public EmployeeDto() {
        super();
    }

    public EmployeeDto(String name, String lastname, String email, String cleartextPassword) {
        this.setCleartextTransientPassword(cleartextPassword);
        this.setName(name);
        this.setLastname(lastname);
        this.setUsername(email);
        this.setEmail(email);
        setRoleIncompleteRegisteredUser();
    }

    public EmployeeDto(String name, String lastname, String email, @Valid PasswordDto passwordDto) {
        super(email, passwordDto);
        this.setName(name);
        this.setLastname(lastname);
        this.setEmail(email);
    }

    @JsonProperty(value = "name")
    public String getName() {
        return name;
    }

    @JsonProperty(value = "name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty(value = "lastname")
    public String getLastname() {
        return lastname;
    }

    @JsonProperty(value = "lastname")
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @JsonProperty(value = "email")
    public String getEmail() {
        return email;
    }

    @JsonProperty(value = "email")
    public void setEmail(String email) {
        this.email = email.toLowerCase();
        setUsername(this.email);
    }


//    @JsonProperty(value = "password", access = JsonProperty.Access.READ_WRITE) // DEBUG
    @JsonProperty(value = "password", access = JsonProperty.Access.WRITE_ONLY) // PROD
    public String getCleartextTransientPassword() {
        return this.cleartextTransientPassword;
    }

//    @JsonProperty(value = "password", access = JsonProperty.Access.READ_WRITE) // DEBUG
    @JsonProperty(value = "password", access = JsonProperty.Access.WRITE_ONLY) // PROD
    public void setCleartextTransientPassword(@Validated
//                                              @PasswordLengthValidation
//                                              @PasswordPolicyValidation
                                                      String cleartextTransientPassword) {
        this.cleartextTransientPassword = cleartextTransientPassword;
        setPassword(this.cleartextTransientPassword);
    }

    public void updatePassword(@Valid
                               @PasswordPolicyValidation
                               @PasswordLengthValidation
                               @PasswordNonReusePolicyValidation
                                       String newCleartextPasswor) {
        if (this.passwordDto != null) {
            this.passwordDto.setClearTextPassword(newCleartextPasswor);
        } else {
            this.passwordDto = new PasswordDto();
            this.passwordDto.setClearTextPassword(newCleartextPasswor);
//            throw new PasswordRequirementException("ERROR: this.passwordDto NOT INITIAZLIZED"); ?
        }
    }


//        @JsonProperty(value = "password", access = JsonProperty.Access.READ_WRITE) // DEBUG
    @JsonIgnore // PROD
//    @JsonProperty(value = "password", access = JsonProperty.Access.WRITE_ONLY)
    public void setPassword(@Valid
                            @PasswordLengthValidation
                            @PasswordPolicyValidation
                                    String cleartextTransientPassword) {
        this.makeFullyDeactivated();
        this.passwordDto = new PasswordDto();
        passwordDto.setUserDto(this);
        passwordDto.setClearTextPassword(cleartextTransientPassword);
        if (passwordDto.isHashedPasswordReady()) {
//            this.makeFullyActivated();
//            setCleartextTransientPassword(null);
            setRoleEmployee();
        } else {
            setRoleIncompleteRegisteredUser();
            throw new PasswordRequirementException("ERROR: this.passwordDto SETTER failed isIsHashedPasswordReady");
        }
    }


//    public void make(@Autowired GrantedAuthority grantedAuthority) {
//
//        // NOTE .email is case-insensitive
//        // NOTE .email will never be null because of validation rules
//        // NOTE run normalized(...) only one, by checking if .id has already been set != null
//
//        if (this.getEmail() != null) {
//            setAuthorities(Set.of(grantedAuthority));
//            setEmail(this.getEmail().toLowerCase());
//        }
//
////        if (this.getPassword() != null) {
////            setPassword(this.getPassword());
////        }
////
////
////        if (this.getId() != null) {
////            setPassword(this.getPassword());
////        }
//    }


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
