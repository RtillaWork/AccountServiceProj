package account.security.entity;

import account.security.RegisteredUserGrantedAuthorityImpl;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;
import java.util.Set;


//@Transactional(Transactional.TxType.NEVER)
@MappedSuperclass
public class UserDetailsDto implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;

    @JsonIgnore
    protected String username;

    @Transient
    private String transientPassword;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonIgnore
    @NotNull
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    protected PasswordDto passwordDto;

    @JsonIgnore
    protected boolean accountNonExpired;

    @JsonIgnore
    protected boolean accountNonlocked;

    @JsonIgnore
    protected boolean credentialstNonExpired;

    @JsonIgnore
    protected boolean enabled;

    @JsonIgnore
    @ElementCollection(fetch = FetchType.EAGER)
    protected Set<GrantedAuthority> authorities;


    public UserDetailsDto() {
        this.accountNonExpired = true;
        this.accountNonlocked = true;
        this.credentialstNonExpired = true;
        this.enabled = true;
        this.authorities = Set.of(new RegisteredUserGrantedAuthorityImpl());
//        this.passwordDto = new PasswordDto();
    }

    public UserDetailsDto(String username, String transientPassword) {

        this.username = username;
        this.passwordDto = new PasswordDto(transientPassword);
        this.accountNonExpired = true;
        this.accountNonlocked = true;
        this.credentialstNonExpired = true;
        this.enabled = true;
    }

    public UserDetailsDto(String username, String transientPassword, boolean accountNonExpired, boolean accountNonlocked, boolean credentialstNonExpired, boolean enabled, Set<GrantedAuthority> authorities) {

        this.username = username;
        this.passwordDto = new PasswordDto(transientPassword);
        this.accountNonExpired = accountNonExpired;
        this.accountNonlocked = accountNonlocked;
        this.credentialstNonExpired = credentialstNonExpired;
        this.enabled = enabled;
        this.authorities = authorities;
    }

    public UserDetailsDto(String username, PasswordDto passwordDto) {

        this.username = username;
        this.passwordDto = passwordDto;

        this.accountNonExpired = true;
        this.accountNonlocked = true;
        this.credentialstNonExpired = true;
        this.enabled = true;
    }

    public UserDetailsDto(String username, PasswordDto passwordDto, boolean accountNonExpired, boolean accountNonlocked, boolean credentialstNonExpired, boolean enabled, Set<GrantedAuthority> authorities) {

        this.username = username;
        this.passwordDto = passwordDto;
        this.accountNonExpired = accountNonExpired;
        this.accountNonlocked = accountNonlocked;
        this.credentialstNonExpired = credentialstNonExpired;
        this.enabled = enabled;
        this.authorities = authorities;
    }


    public Long getId() {
        return id;
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public PasswordDto getPasswordDto() {
        return this.passwordDto;
    }

    public void updatePasswordDto(PasswordDto passwordDto) {
        this.passwordDto = passwordDto;
    }

    public void updatePassword(String transientPassword) {
        this.passwordDto.setPassword(transientPassword);
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public String getPassword() {
        return getPasswordDto().getPassword();
    }

    //    @JsonIgnore
    public String getTransientPassword() {
        return this.transientPassword;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonAlias("password")
    @Validated
    public void setTransientPassword( String transientPassword) {
        this.passwordDto = new PasswordDto(transientPassword);
    }

//    public void setPasswordDTO(PasswordDTO passwordDTO) {
//        this.passwordDTO = passwordDTO;
//    }


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
    @JsonIgnore
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
    @JsonIgnore
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
    @JsonIgnore
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
    @JsonIgnore
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
    @JsonIgnore
    public boolean isEnabled() {
        return this.enabled;
    }

}
