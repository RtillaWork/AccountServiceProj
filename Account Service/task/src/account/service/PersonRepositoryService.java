package account.service;

import account.entity.Person;
//import account.exception.UserAlreadyExistsException;
import account.repository.PersonRepository;
import account.security.EmployeeGrantedAuthorityImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonRepositoryService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Optional<Person> save(Person person) {
        person.init(passwordEncoder, new EmployeeGrantedAuthorityImpl());
        return Optional.ofNullable(personRepository.save(person));
    }

    public Optional<Person> findByEmail(String username) {
        return personRepository.findByEmail(username.toLowerCase());
    }

    public Optional<Person> findByEmail(Person person) {
            return findByEmail(person.getEmail());
    }

    public Optional<Person> findByUsername(String username) {
        return findByEmail(username);
    }


}

