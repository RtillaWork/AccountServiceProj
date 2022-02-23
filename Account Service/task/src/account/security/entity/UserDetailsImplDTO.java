package account.security.entity;

import account.security.RegisteredUserGrantedAuthorityImpl;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Set;

@MappedSuperclass
@Transactional
public class UserDetailsImplDTO implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;

    @JsonIgnore
    protected String username;

//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
////    @NotEmpty
////    @Size(min = 12)
//    @Transient
//    protected String password;

    //    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = true, fetch = FetchType.EAGER)
    protected PasswordDTO passwordDTO;

    @Transient
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

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


    public UserDetailsImplDTO() {
        this.accountNonExpired = true;
        this.accountNonlocked = true;
        this.credentialstNonExpired = true;
        this.enabled = true;
        this.authorities = Set.of(new RegisteredUserGrantedAuthorityImpl());
        this.passwordDTO = new PasswordDTO();
    }

    public UserDetailsImplDTO(String username, String password) {

        this.username = username;
        this.passwordDTO = new PasswordDTO(password);
        this.accountNonExpired = true;
        this.accountNonlocked = true;
        this.credentialstNonExpired = true;
        this.enabled = true;
    }

    public UserDetailsImplDTO(String username, String password,
                              boolean accountNonExpired,
                              boolean accountNonlocked,
                              boolean credentialstNonExpired,
                              boolean enabled,
                              Set<GrantedAuthority> authorities) {

        this.username = username;
        this.passwordDTO = new PasswordDTO(password);
        this.accountNonExpired = accountNonExpired;
        this.accountNonlocked = accountNonlocked;
        this.credentialstNonExpired = credentialstNonExpired;
        this.enabled = enabled;
        this.authorities = authorities;
    }

    public UserDetailsImplDTO(String username, PasswordDTO passwordDTO) {

        this.username = username;
        this.passwordDTO = passwordDTO;

        this.accountNonExpired = true;
        this.accountNonlocked = true;
        this.credentialstNonExpired = true;
        this.enabled = true;
    }

    public UserDetailsImplDTO(String username, PasswordDTO passwordDTO,
                              boolean accountNonExpired,
                              boolean accountNonlocked,
                              boolean credentialstNonExpired,
                              boolean enabled,
                              Set<GrantedAuthority> authorities) {

        this.username = username;
        this.passwordDTO = passwordDTO;
        this.accountNonExpired = accountNonExpired;
        this.accountNonlocked = accountNonlocked;
        this.credentialstNonExpired = credentialstNonExpired;
        this.enabled = enabled;
        this.authorities = authorities;
    }


    public Long getId() {
        return id;
    }

    //    public String getPassword() {
//        return passwordDTO.getPassword();
//    }

    @JsonIgnore
    public String getTransientPassword() {
        return this.password;
    }

    public PasswordDTO getPasswordDTO() {
        return this.passwordDTO;
    }

    @JsonIgnore
    public String getPassword() {
        return getPasswordDTO().getPassword();
    }


    //    public void setPassword(String password) {
//        this.password = password;
//    }
//
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonAlias("new_password")
    public void setPassword(String password) {
        this.password = password;
        this.passwordDTO = new PasswordDTO(password);
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
