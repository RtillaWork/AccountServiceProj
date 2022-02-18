package account.service;

import account.entity.Person;
import account.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonRepositoryService {

    @Autowired
    PersonRepository personRepository;

    public Optional<Person> save(Person person) {
        return Optional.ofNullable(personRepository.save(person));
        }
    }

