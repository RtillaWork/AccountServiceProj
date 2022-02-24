package account.service;

import account.entity.PersonDto;
//import account.exception.UserAlreadyExistsException;
import account.repository.PersonRepository;
import account.security.entity.PasswordDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
//@Transactional(Transactional.TxType.NEVER)
public class PersonRepositoryService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PasswordRepositoryService passwordDTOrs;

    @Autowired
    PasswordEncoder passwordEncoder;

//    @Transactional(Transactional.TxType.NEVER)
    public PersonDto save(PersonDto personDTO) {
//        person.init(passwordEncoder, new EmployeeGrantedAuthorityImpl());

//        personDTO.build(new EmployeeGrantedAuthorityImpl());
        PasswordDto password = passwordDTOrs.save(personDTO.getPasswordDto());
//        PersonDto p = personRepository.save(personDTO);
        return p;

    }
//
    public Optional<PersonDto> findByEmail(String username) {
        return personRepository.findByEmail(username.toLowerCase());
    }

    public Optional<PersonDto> findByEmail(PersonDto personDTO) {
            return findByEmail(personDTO.getEmail());
    }

    public Optional<PersonDto> findByUsername(String username) {
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

