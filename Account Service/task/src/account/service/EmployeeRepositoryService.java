package account.service;

import account.entity.EmployeeDto;
//import account.exception.UserAlreadyExistsException;
import account.repository.EmployeeRepository;
import account.security.entity.PasswordDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Service
//@Transactional(Transactional.TxType.NEVER)
public class EmployeeRepositoryService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    PasswordRepositoryService passwordRepositoryService;

    public Optional<EmployeeDto> save(@Valid EmployeeDto employeeDTO) {
//        PasswordDto passwd = employeeDTO.getPasswordDto();
//        passwd.setUser(employeeDTO);
        Optional<EmployeeDto> updatedEmployeeDto = Optional.empty();

        try {
            employeeDTO.setRoleEmployee();
            updatedEmployeeDto = Optional.of(employeeRepository.save(employeeDTO));
        } catch (IllegalArgumentException ex) {
            throw ex;
        }
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

    public Optional<EmployeeDto> updatePassword(Principal principal, PasswordDto newPasswordDTO) {
        EmployeeDto employeeDTO = findByPrincipal(principal).orElseThrow();
        if (employeeDTO.getPasswordDto().getCleartextNewPassword() == null) {
            System.err.println("public EmployeeDto updatePassword ersonDTO.getPasswordDto().getClearTextPassword(): IS NULLLL");
        } else {
            System.err.println("public EmployeeDto updatePassword( employeeDTO.getPasswordDto().getClearTextPassword() : " + employeeDTO.getPasswordDto().getCleartextNewPassword());
        }

//        newPasswordDTO.setClearTextPassword(newPasswordDTO.getClearTextPassword());
        employeeDTO.updatePassword(newPasswordDTO.getCleartextNewPassword());
        //        EmployeeDto p = save(employeeDTO);
        Optional<EmployeeDto> p = save(employeeDTO); //, employeeDTO.getPasswordDto());

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

    public Optional<EmployeeDto> update(Principal principal, @Validated PasswordDto newPasswordDto) {
        String newCleartextPassword = newPasswordDto.getCleartextNewPassword();
        EmployeeDto employeeDTO = findByPrincipal(principal).orElseThrow();
//           PasswordDto passwordDto = employeeDTO.getPasswordDto();
        employeeDTO.updatePassword(newCleartextPassword);
//           passwordRepositoryService.delete(employeeDTO.getPasswordDto());
        Optional<EmployeeDto> updatedWithPassword = Optional.empty();
        try {
            updatedWithPassword = Optional.of(employeeRepository.save(employeeDTO));
        } catch (IllegalArgumentException ex) {
            throw ex;
        }
        return updatedWithPassword;
    }
}

