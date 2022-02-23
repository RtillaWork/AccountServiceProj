package account.service;

import account.entity.PersonDTO;
//import account.exception.UserAlreadyExistsException;
import account.repository.PersonDTORepository;
import account.security.entity.PasswordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class PersonRepositoryService {

    @Autowired
    PersonDTORepository personDTORepository;

    @Autowired
    PasswordDTORepositoryService passwordDTOrs;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    public Optional<PersonDTO> save(PersonDTO personDTO) {
//        person.init(passwordEncoder, new EmployeeGrantedAuthorityImpl());

//        personDTO.build(new EmployeeGrantedAuthorityImpl());
        PasswordDTO password = passwordDTOrs.save(personDTO.getPasswordDTO());
        Optional<PersonDTO> p = Optional.ofNullable(personDTORepository.save(personDTO));
        return p;

    }
//
    public Optional<PersonDTO> findByEmail(String username) {
        return personDTORepository.findByEmail(username.toLowerCase());
    }

    public Optional<PersonDTO> findByEmail(PersonDTO personDTO) {
            return findByEmail(personDTO.getEmail());
    }

    public Optional<PersonDTO> findByUsername(String username) {
        return findByEmail(username);
    }
//
//    public Optional<PersonDTO> findByPrincipal(Principal principal) {
//        Optional<PersonDTO> person = findByUsername(principal.getName());
//        return person;
//    }
//
////    public Optional<PersonDTO>  updatePassword(Principal principal, PasswordDTO newPasswordDTO) {
////        PersonDTO personDTO = findByPrincipal(principal).orElseThrow();
////        if (newPasswordDTO.isValid()) {
////            personDTO.setPassword(newPasswordDTO);
////            Optional<PersonDTO> p = save(personDTO);
////            return p;
////        } else {
////            return Optional.empty();
////        }
////    }


}

