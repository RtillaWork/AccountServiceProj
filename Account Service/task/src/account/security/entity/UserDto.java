package account.security.entity;

import account.entity.validation.PasswordLengthValidation;
import account.entity.validation.PasswordNonReusePolicyValidation;
import account.entity.validation.PasswordPolicyValidation;
import account.exception.PasswordRequirementException;
import account.security.authority.EmployeeGrantedAuthorityImpl;
import account.security.authority.IncompleteRegisteredUserGrantedAuthorityImpl;
import account.security.authority.RegisteredUserGrantedAuthorityImpl;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Set;


@MappedSuperclass
public class UserDto implements UserDetails {

    public static final String UNIDENTIFIED_USER_USERNAME = "UNIDENTIFIED_USER_USERNAME";
    public static final String UNIDENTIFIED_USER_PASSWORD = "UNIDENTIFIED_USER_PASSWORD";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @NotBlank
    protected String username; // = UNIDENTIFIED_USER_USERNAME;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "password_id")
    protected PasswordDto passwordDto;

    protected boolean accountNonExpired;
    protected boolean accountNonlocked;
    protected boolean credentialstNonExpired;
    protected boolean enabled;

    @ElementCollection(fetch = FetchType.EAGER)
    protected Set<GrantedAuthority> authorities;

    public UserDto() {
        setRoleIncompleteRegisteredUser();
    }

    public UserDto(@NotNull String username,
//                   @Valid
//    @PasswordPolicyValidation
//    @PasswordLengthValidation
//    @PasswordNonReusePolicyValidation
                   PasswordDto passwordDto) {
        this.setUsername(username);
        this.setPasswordDto(passwordDto);
        setRoleRegisteredUser();
    }


    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Long getId() {
        return id;
    }

    //    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonIgnore
    public void setPasswordDto(@Valid PasswordDto passwordDto) {
        this.passwordDto = passwordDto;
        // TODO SETROLEx here instead of UserDto(..., PasswordDto) ?
    }

    //    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // DEBUG, need to comment out user field in PasswordDto
    @JsonIgnore // TODO NOTE watch out for deserialization overflow if removed
    public PasswordDto getPasswordDto() {
        return this.passwordDto;
    }

    //    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // DEBUG
    @JsonIgnore
    public String getPassword() {
        return this.passwordDto.getHashedPassword();
    }

    /////////////////////////////////////
    // UserDetails
    ////////////////////////////////////
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.authorities != null) {
            return this.authorities;
        } else {
            throw new RuntimeException("FROM USERDTO NULL VALUE EXCEPTION: this.authorities CANNOT BE NULL!");
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
        this.username = username.toLowerCase();
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


    ////////////////////////////////////
    // Utility methods
    ///////////////////////////////////
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

    public void setRoleEmployee() {
        setAuthorities(Set.of(new EmployeeGrantedAuthorityImpl()));
        makeFullyActivated();
    }

    public void setRoleIncompleteRegisteredUser() {
        setAuthorities(Set.of(new IncompleteRegisteredUserGrantedAuthorityImpl()));
        makeFullyDeactivated();
    }

    public void setRoleRegisteredUser() {
        setAuthorities(Set.of(new RegisteredUserGrantedAuthorityImpl()));
        makeFullyActivated();
    }
}
