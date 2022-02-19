package account.service;

import account.controller.UserController;
import account.entity.Person;
import account.exception.UserAlreadyExistsException;
import account.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.Locale;

@Service
public class PersonRepositoryService {

    @Autowired
    PersonRepository personRepository;

    public Person save(Person person) {
        if (person == null) {
            throw new InvalidParameterException("EXCEPTION: Person person object is null");
        } else if (personRepository.findByEmail(person.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException();
        } else {
            person.normalized();
            return personRepository.save(person);
        }
    }
}

