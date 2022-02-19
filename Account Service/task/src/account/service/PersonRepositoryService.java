package account.service;

import account.controller.UserController;
import account.entity.Person;
import account.exception.UserAlreadyExistsException;
import account.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.Locale;
import java.util.Optional;

@Service
public class PersonRepositoryService {

    @Autowired
    PersonRepository personRepository;

    public Optional<Person> save(Person person) {
        person.normalized();
        return Optional.ofNullable(personRepository.save(person));
    }

    public Optional<Person> findByEmail(Person person) {
            return personRepository.findByEmail(person.getEmail());
    }

    public Optional<Person> findByEmail(String username) {
        return personRepository.findByEmail(username);
    }
    public Optional<Person> findByUsername(String username) {
        return personRepository.findByEmail(username);
    }


}

