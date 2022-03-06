package account.controller;

import account.entity.EmployeeDto;
//import account.exception.UserAlreadyExistsException;
import account.entity.validation.PasswordLengthValidation;
import account.entity.validation.PasswordNonReusePolicyValidation;
import account.entity.validation.PasswordPolicyValidation;
import account.route.v1.ChangePass;
import account.route.v1.Signup;
import account.security.entity.PasswordDto;
import account.service.EmployeeRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.security.Principal;

@RestController
@Validated
public class UserController {

    @Autowired
    EmployeeRepositoryService prs;


    @PostMapping(path = Signup.PATH)
    @ResponseBody
    public ResponseEntity<EmployeeDto> signup( @Valid
                                                   @PasswordLengthValidation
                                                   @PasswordPolicyValidation
                                                   @RequestBody EmployeeDto employeeDTO) {
        if (employeeDTO == null) {
            throw new ValidationException("EXCEPTION: person object is null");
        } else  if (prs.findByEmail(employeeDTO).isPresent()) {
            throw new DataIntegrityViolationException("EXCEPTION: email already exists");
        }
        else {
            EmployeeDto p = prs.save(employeeDTO);
//            PersonDTO p = employeeDTO; // temp deleteme
            return new ResponseEntity<EmployeeDto>(p, HttpStatus.OK);
        }
    }

    @PostMapping(path = ChangePass.PATH)
    @ResponseBody
    public ResponseEntity<EmployeeDto> changePassword(@Valid @RequestBody PasswordDto newPasswordDTO,
                                                      Principal principal) {
        System.out.println(" public ResponseEntity<EmployeeDto> changePassword( newPasswordDTO: " + newPasswordDTO.getClearTextPassword());
        EmployeeDto p =  prs.update(principal, newPasswordDTO);
        return new ResponseEntity<>(p, HttpStatus.OK);


    }
}



