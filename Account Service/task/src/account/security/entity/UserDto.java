package account.security.entity;

import account.entity.validation.PasswordLengthValidation;
import account.entity.validation.PasswordNonReusePolicyValidation;
import account.entity.validation.PasswordPolicyValidation;
import account.exception.PasswordRequirementException;
import account.security.RegisteredUserGrantedAuthorityImpl;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Set;


@MappedSuperclass
@Validated
public class UserDto implements UserDetails {

    public static final String NULL_USERNAME = "NULL_USERNAME";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    protected String username = NULL_USERNAME;

    //    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
//    @NotNull
//    @Valid
//    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "password_id")
    @Valid
    protected PasswordDto passwordDto;

    protected boolean accountNonExpired;

    protected boolean accountNonlocked;

    protected boolean credentialstNonExpired;

    protected boolean enabled;

    @ElementCollection(fetch = FetchType.EAGER)
    protected Set<GrantedAuthority> authorities;


    @Transient
    @PasswordLengthValidation(message = "The password length must be at least 12!")
    @PasswordPolicyValidation
//    @PasswordNonReusePolicyValidation
    private String cleartextTransientPassword = "";


    public UserDto() {
        setAuthorities(Set.of(new RegisteredUserGrantedAuthorityImpl()));
//        this.passwordDto = new PasswordDto();
//        this.password = new PasswordDto(cleartextTransientPassword);
//        makeFullyDeactivated();
    }

//    public UserDetailsDto(String username, String transientPassword) {
//        this.username = username;
//        this.password = new PasswordDto(transientPassword);
//        makeFullyActivated();
//
//    }

//    public UserDetailsDto(String username, String transientPassword, boolean accountNonExpired, boolean accountNonlocked, boolean credentialstNonExpired, boolean enabled, Set<GrantedAuthority> authorities) {
//
//        this.username = username;
//        this.password = new PasswordDto(transientPassword);
//        this.accountNonExpired = accountNonExpired;
//        this.accountNonlocked = accountNonlocked;
//        this.credentialstNonExpired = credentialstNonExpired;
//        this.enabled = enabled;
//        setAuthorities(authorities);
//    }
//
//    public UserDetailsDto(String username, PasswordDto password) {
//        this.username = username;
//        this.password = password;
//        makeFullyActivated();
//    }
//
//    public UserDetailsDto(String username, PasswordDto password, boolean accountNonExpired, boolean accountNonlocked, boolean credentialstNonExpired, boolean enabled, Set<GrantedAuthority> authorities) {
//        this.username = username;
//        this.password = password;
//        this.accountNonExpired = accountNonExpired;
//        this.accountNonlocked = accountNonlocked;
//        this.credentialstNonExpired = credentialstNonExpired;
//        this.enabled = enabled;
//        setAuthorities(authorities);
//    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Long getId() {
        return id;
    }

//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
//    public PasswordDto getPassword(boolean withHashRemoved) {
//        withHashRemoved = false; // TODO verify if it's safe default
//        String hash_removed = "HASH_REMOVED";
//        if (withHashRemoved) {
//            this.passwordDto.setHashedPassword(hash_removed);
//            return this.passwordDto;
//        }
//        else {
//            return this.passwordDto;
//        }
//    }

    //    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonIgnore
    public void setPasswordDto(PasswordDto passwordDto) {
        this.passwordDto = passwordDto;
    }

    //    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonIgnore
    public PasswordDto getPasswordDto() {
        return this.passwordDto;
    }

//    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // DEBUG
    @JsonIgnore
    public String getPassword() {
        return this.passwordDto.getHashedPassword();
    }

    public void updatePassword(String newCleartextPasswor) {
        if (this.passwordDto != null) {
            this.passwordDto.setHashedPassword(newCleartextPasswor);
        } else {
            throw new PasswordRequirementException("ERROR: this.passwordDto NOT INITIZLIZED");
        }
    }

    //    @JsonProperty(value="password", access = JsonProperty.Access.READ_ONLY)
    public String getCleartextTransientPassword() {
        return cleartextTransientPassword;
    }

//    @JsonProperty(value = "password", access = JsonProperty.Access.READ_WRITE) // DEBUG
    @JsonProperty(value = "password", access = JsonProperty.Access.WRITE_ONLY)
    public void setCleartextTransientPassword(@PasswordLengthValidation @PasswordPolicyValidation String cleartextTransientPassword) {
        this.setPassword(cleartextTransientPassword);
    }

//    @JsonProperty(value = "password", access = JsonProperty.Access.WRITE_ONLY)
    @JsonIgnore
    public void setPassword(String cleartextTransientPassword) {
        this.makeFullyDeactivated();
        this.cleartextTransientPassword = cleartextTransientPassword;
        this.passwordDto = new PasswordDto();
        passwordDto.setHashedPassword(this.cleartextTransientPassword);
        if (passwordDto.isHashedPasswordReady()) {
            this.makeFullyActivated();
//            setCleartextTransientPassword(null);
        } else {
            System.out.println("DEBUG PASSWORD DTO CREATION ERROR");
            throw new PasswordRequirementException("ERROR: this.passwordDto SETTER failed isIsHashedPasswordReady");
        }
    }


    // UserDetail

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.authorities != null) {
            return this.authorities;
        } else {
            throw new RuntimeException("NULL VALUE EXCEPTION: this.authorities CANNOT BE NULL!");
        }
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        if (this.username != null) {
            return this.username;
        } else {
            throw new RuntimeException("NULL VALUE EXCEPTION: this.username CANNOT BE NULL!");
        }
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return this.accountNonlocked;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return this.credentialstNonExpired;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return this.enabled;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public void setAccountNonlocked(boolean accountNonlocked) {
        this.accountNonlocked = accountNonlocked;
    }

    public void setCredentialstNonExpired(boolean credentialstNonExpired) {
        this.credentialstNonExpired = credentialstNonExpired;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


    public void setAuthorities(Set<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }


    // Utility methods

    public void makeFullyDeactivated() {
        this.accountNonExpired = false;
        this.accountNonlocked = false;
        this.credentialstNonExpired = false;
        this.enabled = false;
    }

    public void makeFullyActivated() {
        this.accountNonExpired = true;
        this.accountNonlocked = true;
        this.credentialstNonExpired = true;
        this.enabled = true;
    }
}
