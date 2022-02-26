package account.security.entity;

import account.entity.validation.PasswordLengthValidation;
import account.security.RegisteredUserGrantedAuthorityImpl;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Set;


//@Transactional(Transactional.TxType.NEVER)
@MappedSuperclass
@Validated
public class UserDetailsDto implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    protected String username;

//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
//    @NotNull
//    @Valid
//    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "password_id")
    protected PasswordDto password;

    protected boolean accountNonExpired;

    protected boolean accountNonlocked;

    protected boolean credentialstNonExpired;

    protected boolean enabled;

    @ElementCollection(fetch = FetchType.EAGER)
    protected Set<GrantedAuthority> authorities;

//    @PasswordLengthValidation(message =  "The password length must be at least 12!")
    @Transient
    private String cleartextTransientPassword = "";



    public UserDetailsDto() {
        setAuthorities(Set.of(new RegisteredUserGrantedAuthorityImpl()));
        this.password = new PasswordDto();
//        this.password = new PasswordDto(cleartextTransientPassword);
        makeFullyDeactivated();
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

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public PasswordDto getPasswordDto() {
        return this.password;
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public void setPassword(PasswordDto passwordDto) {
        this.password = passwordDto;
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public String getPassword() {
        return this.password.getHashedPassword();
    }

    public void updatePassword(String newCleartextPasswor) {
        this.password.setHashedPassword(newCleartextPasswor);
    }

    @JsonProperty(value="newpassword", access = JsonProperty.Access.READ_WRITE)
    public String getCleartextTransientPassword() {
        return cleartextTransientPassword;
    }

    @JsonProperty(value="newpassword", access = JsonProperty.Access.READ_WRITE)
    public void setCleartextTransientPassword(String cleartextTransientPassword) {
        this.cleartextTransientPassword = cleartextTransientPassword;
        password.setHashedPassword( this.cleartextTransientPassword);
        if (password.isIsHashedPasswordReady()) {
            this.makeFullyActivated();
//            setCleartextTransientPassword(null);
        }
    }

    /**
     * Returns the authorities granted to the user. Cannot return <code>null</code>.
     *
     * @return the authorities, sorted by natural key (never <code>null</code>)
     */
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    /**
     * Returns the username used to authenticate the user. Cannot return <code>null</code>.
     *
     * @return the username (never <code>null</code>)
     */
    @Override
//    @JsonIgnore
    public String getUsername() {
        return this.username;
    }

    /**
     * Indicates whether the user's account has expired. An expired account cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user's account is valid (ie non-expired),
     * <code>false</code> if no longer valid (ie expired)
     */
    @Override
//    @JsonIgnore
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    /**
     * Indicates whether the user is locked or unlocked. A locked user cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user is not locked, <code>false</code> otherwise
     */
    @Override
//    @JsonIgnore
    public boolean isAccountNonLocked() {
        return this.accountNonlocked;
    }

    /**
     * Indicates whether the user's credentials (password) has expired. Expired
     * credentials prevent authentication.
     *
     * @return <code>true</code> if the user's credentials are valid (ie non-expired),
     * <code>false</code> if no longer valid (ie expired)
     */
    @Override
//    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return this.credentialstNonExpired;
    }

    /**
     * Indicates whether the user is enabled or disabled. A disabled user cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
     */
    @Override
//    @JsonIgnore
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
