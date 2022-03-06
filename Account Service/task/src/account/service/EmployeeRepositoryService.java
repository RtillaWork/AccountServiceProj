package account.service;

import account.entity.EmployeeDto;
//import account.exception.UserAlreadyExistsException;
import account.repository.EmployeeRepository;
import account.security.authority.EmployeeGrantedAuthorityImpl;
import account.security.entity.PasswordDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.Locale;
import java.util.Optional;

@Service
//@Transactional(Transactional.TxType.NEVER)
public class EmployeeRepositoryService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    PasswordRepositoryService passwordRepositoryService;

    public EmployeeDto save(EmployeeDto employeeDTO) {
//        PasswordDto passwd = employeeDTO.getPasswordDto();
//        passwd.setUser(employeeDTO);
        employeeDTO.setRoleEmployee();
       EmployeeDto updatedEmployeeDto = employeeRepository.save(employeeDTO);
        return updatedEmployeeDto;
    }

    public Optional<EmployeeDto> findByEmail(String username) {
        return employeeRepository.findByEmail(username.toLowerCase());
    }

    public Optional<EmployeeDto> findByEmail(EmployeeDto employeeDTO) {
        return findByEmail(employeeDTO.getEmail());
    }

    public Optional<EmployeeDto> findByUsername(String username) {
        return findByEmail(username.toLowerCase());
    }

    public Optional<EmployeeDto> findByPrincipal(Principal principal) {
        Optional<EmployeeDto> employee = findByUsername(principal.getName());
        return employee;
    }

    public EmployeeDto updatePassword(Principal principal, PasswordDto newPasswordDTO) {
        EmployeeDto employeeDTO = findByPrincipal(principal).orElseThrow();
        if (employeeDTO.getPasswordDto().getClearTextPassword() == null) {
            System.out.println("public EmployeeDto updatePassword ersonDTO.getPasswordDto().getClearTextPassword(): IS NULLLL");
        } else {
            System.out.println("public EmployeeDto updatePassword( employeeDTO.getPasswordDto().getClearTextPassword() : " + employeeDTO.getPasswordDto().getClearTextPassword());
        }

//        newPasswordDTO.setClearTextPassword(newPasswordDTO.getClearTextPassword());
        employeeDTO.updatePassword(newPasswordDTO.getClearTextPassword());
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
           EmployeeDto p = employeeRepository.save(employeeDTO);
           return p;
    }




}

