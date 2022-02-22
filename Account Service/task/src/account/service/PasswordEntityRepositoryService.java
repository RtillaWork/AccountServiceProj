package account.service;

import account.entity.Person;
import account.repository.PasswordEntityRepository;
import account.security.entity.PasswordEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class PasswordEntityRepositoryService {

    @Autowired
    private PasswordEntityRepository per;

    Person findByPerson(Person person) {
        PasswordEntity p = per.findByPerson(person).orElseThrow();
        return null;

    }

    Person findByUserName(Person person) {
return null;
    }

    Person findByEmail(Person person) {
        return null;

    }

    Person findByPrincipal(Person person) {
        return null;

    }
}
