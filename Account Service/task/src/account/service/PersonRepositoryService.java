package account.service;

import account.entity.PersonDto;
//import account.exception.UserAlreadyExistsException;
import account.repository.PersonRepository;
import account.security.EmployeeGrantedAuthorityImpl;
import account.security.entity.PasswordDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.annotation.Validated;

import javax.transaction.RollbackException;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import java.util.Optional;
import java.util.Set;

@Service
//@Transactional(Transactional.TxType.NEVER)
public class PersonRepositoryService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PasswordRepositoryService passwordRepositoryService;

    @Transactional
    public PersonDto save(@Validated PersonDto personDTO) {
        PersonDto p = personDTO;
        try {
            System.out.println(" System.out.println(personDTO.getCleartextTransientPassword());: " + personDTO.getCleartextTransientPassword());
            PasswordDto passwordDto = passwordRepositoryService.save(personDTO.getPasswordDto());
            System.out.println(" AFTER SAVE System.out.println(personDTO.getCleartextTransientPassword());: " + passwordDto.getHashedPassword());
            personDTO.setPassword(passwordDto);
            p =  personRepository.save(personDTO);
        }
        catch (TransactionSystemException ex) {
            System.out.println("TransactionSystemException");
            throw ex;
        }

//        catch (RollbackException ex) {
//            System.out.println("RollbackException");
//            throw ex;
//        }


//        person.init(passwordEncoder, new EmployeeGrantedAuthorityImpl());
//        personDTO.build(new EmployeeGrantedAuthorityImpl());
//        personDTO.make(new EmployeeGrantedAuthorityImpl());
//        PasswordDto password = passwordDTOrs.save(personDTO.getPasswordDto());
//        PersonDto p = personRepository.save(personDTO);
//        return p;
//        personDTO
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

