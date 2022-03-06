package account.service;

import account.entity.EmployeeDto;
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
    public EmployeeDto save(@Validated EmployeeDto employeeDTO) {
//        System.out.println(" System.out.println(employeeDTO.getCleartextTransientPassword());: " + employeeDTO.getCleartextTransientPassword());
//        System.out.println("  EmployeeDto save( System.out.println(employeeDTO.getPasswordDto().getHashedPassword());: " + employeeDTO.getPasswordDto().getHashedPassword());

        PasswordDto passwd = employeeDTO.getPasswordDto();
        passwd.setUser(employeeDTO);
//        EmployeeDto p = employeeDTO;
        employeeDTO.make(new EmployeeGrantedAuthorityImpl());
        System.out.println("  EmployeeDto make());: " + employeeDTO.getUsername());
        System.out.println("  EmployeeDto IS PASSWORDDTO NULL());: " + employeeDTO.getPasswordDto() == null ? " YES " : " NO, HASH IS: " + employeeDTO.getPasswordDto().getHashedPassword() );
        System.out.println("  EmployeeDto IS PASSWORDDTO NULL());: " + employeeDTO.getPasswordDto() == null);

        EmployeeDto updatedEmployeeDto = personRepository.save(employeeDTO);

        return updatedEmployeeDto;

//        return p;

//        person.init(passwordEncoder, new EmployeeGrantedAuthorityImpl());
//        employeeDTO.build(new EmployeeGrantedAuthorityImpl());
//        employeeDTO.make(new EmployeeGrantedAuthorityImpl());
//        PasswordDto password = passwordDTOrs.save(employeeDTO.getPasswordDto());
//        EmployeeDto p = personRepository.save(employeeDTO);
//        return p;
//        employeeDTO
    }

    //    @Transactional
    public EmployeeDto save(@Validated EmployeeDto employeeDTO, @Validated PasswordDto passwordDto) {
//        System.out.println(" System.out.println(employeeDTO.getCleartextTransientPassword());: " + employeeDTO.getCleartextTransientPassword());
        System.out.println(" 2 args EmployeeDto save( System.out.println(employeeDTO.getPasswordDto().getHashedPassword());: " + employeeDTO.getPasswordDto().getHashedPassword());

        PasswordDto passwd = passwordDto; // employeeDTO.getPasswordDto();
        passwd.setUser(employeeDTO);
//        EmployeeDto p = employeeDTO;
        employeeDTO.make(new EmployeeGrantedAuthorityImpl());
        System.out.println(" 2 args EmployeeDto make());: " + employeeDTO.getUsername());

        EmployeeDto updatedEmployeeDto = personRepository.save(employeeDTO);

        return updatedEmployeeDto;

//        return p;

//        person.init(passwordEncoder, new EmployeeGrantedAuthorityImpl());
//        employeeDTO.build(new EmployeeGrantedAuthorityImpl());
//        employeeDTO.make(new EmployeeGrantedAuthorityImpl());
//        PasswordDto password = passwordDTOrs.save(employeeDTO.getPasswordDto());
//        EmployeeDto p = personRepository.save(employeeDTO);
//        return p;
//        employeeDTO
    }

    //
    public Optional<EmployeeDto> findByEmail(String username) {
        return personRepository.findByEmail(username.toLowerCase());
    }

    public Optional<EmployeeDto> findByEmail(EmployeeDto employeeDTO) {
        return findByEmail(employeeDTO.getEmail());
    }

    public Optional<EmployeeDto> findByUsername(String username) {
        return findByEmail(username);
    }

    public Optional<EmployeeDto> findByPrincipal(Principal principal) {
        Optional<EmployeeDto> person = findByUsername(principal.getName());
        return person;
    }

    //
    public EmployeeDto updatePassword(Principal principal, PasswordDto newPasswordDTO) {
        EmployeeDto employeeDTO = findByPrincipal(principal).orElseThrow();
        System.out.println("public EmployeeDto updatePassword(: " + employeeDTO.getUsername());
        if (employeeDTO.getPasswordDto().getClearTextPassword() == null) {
            System.out.println("public EmployeeDto updatePassword ersonDTO.getPasswordDto().getClearTextPassword(): IS NULLLL");
        } else {
            System.out.println("public EmployeeDto updatePassword( employeeDTO.getPasswordDto().getClearTextPassword() : " + employeeDTO.getPasswordDto().getClearTextPassword());
        }

        System.out.println("public EmployeeDto  updatePassword newPasswordDTO.getClearTextPassword(): " + newPasswordDTO.getClearTextPassword());

//        newPasswordDTO.setClearTextPassword(newPasswordDTO.getClearTextPassword());
        employeeDTO.updatePassword(newPasswordDTO.getClearTextPassword());

        System.out.println("newPasswordDTO.getClearTextPassword(): " + newPasswordDTO.getClearTextPassword());

        System.out.println("employeeDTO.get Password(): " + employeeDTO.getPassword() + " employeeDTO.get username(): " + employeeDTO.getUsername() + " employeeDTO.get passwordDto clear text(): " + employeeDTO.getPasswordDto().getClearTextPassword() + " employeeDTO.get passwordDto get hashed EQUALS personDto.geaptssword  " + employeeDTO.getPasswordDto().getHashedPassword().equals(employeeDTO.getPassword()));
//        EmployeeDto p = save(employeeDTO);
        EmployeeDto p = save(employeeDTO); //, employeeDTO.getPasswordDto());

        // personRepository.
        return p;

//        if (newPasswordDTO.isValid()) {
//            employeeDTO.setPassword(newPasswordDTO);
//            Optional<EmployeeDto> p = save(employeeDTO);
//            return p;
//        } else {
//            return Optional.empty();
//        }
    }
       public EmployeeDto update(Principal principal, @Validated @NotNull PasswordDto newPasswordDto) {
           String newCleartextPassword = newPasswordDto.getClearTextPassword();
           EmployeeDto employeeDTO = findByPrincipal(principal).orElseThrow();
//           PasswordDto passwordDto = employeeDTO.getPasswordDto();
           employeeDTO.updatePassword(newCleartextPassword);
//           passwordRepositoryService.delete(employeeDTO.getPasswordDto());
           EmployeeDto p = personRepository.save(employeeDTO);
           return p;
    }




}

