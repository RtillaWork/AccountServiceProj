package account.entity;

import account.route.Api;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Person extends UserDetailsImpl {

    private static final String
            EMAIL_REGEXP = "@" + Api.VALID_DOMAINS.get("Corporate") + "$";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String lastname;

    @NotEmpty
    @Email(regexp = "^(.+)@acme.com$" )
    private String email;

    public Person() { }

    public Person(String name, String lastname, String email, String password) {
        super(email, password);
        this.name = name;
        this.lastname = lastname;
        this.email = email;
    }

    public Person(String username, String password, String name, String lastname, String email) {
        super(username, password);
        this.name = name;
        this.lastname = lastname;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
