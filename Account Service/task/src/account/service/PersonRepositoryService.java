package account.service;

import account.entity.PersonDto;
//import account.exception.UserAlreadyExistsException;
import account.repository.PersonRepository;
import account.security.authority.EmployeeGrantedAuthorityImpl;
import account.security.entity.PasswordDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.Optional;

@Service
//@Transactional(Transactional.TxType.NEVER)
public class PersonRepositoryService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PasswordRepositoryService passwordRepositoryService;

    //    @Transactional
    public PersonDto save(@Validated PersonDto personDTO) {
//        System.out.println(" System.out.println(personDTO.getCleartextTransientPassword());: " + personDTO.getCleartextTransientPassword());
//        System.out.println("  PersonDto save( System.out.println(personDTO.getPasswordDto().getHashedPassword());: " + personDTO.getPasswordDto().getHashedPassword());

        PasswordDto passwd = personDTO.getPasswordDto();
        passwd.setUser(personDTO);
//        PersonDto p = personDTO;
        personDTO.make(new EmployeeGrantedAuthorityImpl());
        System.out.println("  PersonDto make());: " + personDTO.getUsername());
        System.out.println("  PersonDto IS PASSWORDDTO NULL());: " + personDTO.getPasswordDto() == null ? " YES " : " NO, HASH IS: " +personDTO.getPasswordDto().getHashedPassword() );
        System.out.println("  PersonDto IS PASSWORDDTO NULL());: " + personDTO.getPasswordDto() == null);

        PersonDto updatedPersonDto = personRepository.save(personDTO);

        return updatedPersonDto;

//        return p;

//        person.init(passwordEncoder, new EmployeeGrantedAuthorityImpl());
//        personDTO.build(new EmployeeGrantedAuthorityImpl());
//        personDTO.make(new EmployeeGrantedAuthorityImpl());
//        PasswordDto password = passwordDTOrs.save(personDTO.getPasswordDto());
//        PersonDto p = personRepository.save(personDTO);
//        return p;
//        personDTO
    }

    //    @Transactional
    public PersonDto save(@Validated PersonDto personDTO, @Validated PasswordDto passwordDto) {
//        System.out.println(" System.out.println(personDTO.getCleartextTransientPassword());: " + personDTO.getCleartextTransientPassword());
        System.out.println(" 2 args PersonDto save( System.out.println(personDTO.getPasswordDto().getHashedPassword());: " + personDTO.getPasswordDto().getHashedPassword());

        PasswordDto passwd = passwordDto; // personDTO.getPasswordDto();
        passwd.setUser(personDTO);
//        PersonDto p = personDTO;
        personDTO.make(new EmployeeGrantedAuthorityImpl());
        System.out.println(" 2 args PersonDto make());: " + personDTO.getUsername());

        PersonDto updatedPersonDto = personRepository.save(personDTO);

        return updatedPersonDto;

//        return p;

//        person.init(passwordEncoder, new EmployeeGrantedAuthorityImpl());
//        personDTO.build(new EmployeeGrantedAuthorityImpl());
//        personDTO.make(new EmployeeGrantedAuthorityImpl());
//        PasswordDto password = passwordDTOrs.save(personDTO.getPasswordDto());
//        PersonDto p = personRepository.save(personDTO);
//        return p;
//        personDTO
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

    public Optional<PersonDto> findByPrincipal(Principal principal) {
        Optional<PersonDto> person = findByUsername(principal.getName());
        return person;
    }

    //
    public PersonDto updatePassword(Principal principal,  PasswordDto newPasswordDTO) {
        PersonDto personDTO = findByPrincipal(principal).orElseThrow();
        System.out.println("public PersonDto updatePassword(: " + personDTO.getUsername());
        if (personDTO.getPasswordDto().getClearTextPassword() == null) {
            System.out.println("public PersonDto updatePassword ersonDTO.getPasswordDto().getClearTextPassword(): IS NULLLL");
        } else {
            System.out.println("public PersonDto updatePassword( personDTO.getPasswordDto().getClearTextPassword() : " + personDTO.getPasswordDto().getClearTextPassword());
        }

        System.out.println("public PersonDto  updatePassword newPasswordDTO.getClearTextPassword(): " + newPasswordDTO.getClearTextPassword());

//        newPasswordDTO.setClearTextPassword(newPasswordDTO.getClearTextPassword());
        personDTO.updatePassword(newPasswordDTO.getClearTextPassword());

        System.out.println("newPasswordDTO.getClearTextPassword(): " + newPasswordDTO.getClearTextPassword());

        System.out.println("personDTO.get Password(): " + personDTO.getPassword() + " personDTO.get username(): " + personDTO.getUsername() + " personDTO.get passwordDto clear text(): " + personDTO.getPasswordDto().getClearTextPassword() + " personDTO.get passwordDto get hashed EQUALS personDto.geaptssword  " + personDTO.getPasswordDto().getHashedPassword().equals(personDTO.getPassword()));
//        PersonDto p = save(personDTO);
        PersonDto p = save(personDTO); //, personDTO.getPasswordDto());

        // personRepository.
        return p;

//        if (newPasswordDTO.isValid()) {
//            personDTO.setPassword(newPasswordDTO);
//            Optional<PersonDto> p = save(personDTO);
//            return p;
//        } else {
//            return Optional.empty();
//        }
    }
       public PersonDto update(Principal principal, @Validated @NotNull PasswordDto newPasswordDto) {
           String newCleartextPassword = newPasswordDto.getClearTextPassword();
           PersonDto personDTO = findByPrincipal(principal).orElseThrow();
//           PasswordDto passwordDto = personDTO.getPasswordDto();
           personDTO.updatePassword(newCleartextPassword);
//           passwordRepositoryService.delete(personDTO.getPasswordDto());
           PersonDto p = personRepository.save(personDTO);
           return p;
    }




}

