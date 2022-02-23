package account.service;

import account.entity.PersonDTO;
//import account.exception.UserAlreadyExistsException;
import account.repository.PersonRepository;
import account.security.EmployeeGrantedAuthorityImpl;
import account.security.entity.PasswordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.Optional;

@Service
public class PersonRepositoryService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    public Optional<PersonDTO> save(PersonDTO personDTO) {
//        person.init(passwordEncoder, new EmployeeGrantedAuthorityImpl());

        personDTO.build(new EmployeeGrantedAuthorityImpl());
        return Optional.ofNullable(personRepository.save(personDTO));
    }

    public Optional<PersonDTO> findByEmail(String username) {
        return personRepository.findByEmail(username.toLowerCase());
    }

    public Optional<PersonDTO> findByEmail(PersonDTO personDTO) {
            return findByEmail(personDTO.getEmail());
    }

    public Optional<PersonDTO> findByUsername(String username) {
        return findByEmail(username);
    }

    public Optional<PersonDTO> findByPrincipal(Principal principal) {
        Optional<PersonDTO> person = findByUsername(principal.getName());
        return person;
    }

    public Optional<PersonDTO>  updatePassword(Principal principal, PasswordDTO newPasswordDTO) {
        PersonDTO personDTO = findByPrincipal(principal).orElseThrow();
        if (newPasswordDTO.isValid()) {
            personDTO.setPassword(newPasswordDTO);
            Optional<PersonDTO> p = save(personDTO);
            return p;
        } else {
            return Optional.empty();
        }
    }


}

