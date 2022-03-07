package account.controller;

import account.entity.EmployeeDto;
//import account.exception.UserAlreadyExistsException;
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
import java.util.Optional;

@RestController
@Validated
public class UserController {

    @Autowired
    EmployeeRepositoryService prs;


    @PostMapping(path = Signup.PATH, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<EmployeeDto> signup(@Valid
//                                                   @PasswordLengthValidation
//                                                   @PasswordPolicyValidation
                                              @RequestBody EmployeeDto employeeDTO) {
        if (employeeDTO == null) {
            throw new ValidationException("EXCEPTION: person object is null");
        } else if (prs.findByEmail(employeeDTO).isPresent()) {
            throw new DataIntegrityViolationException("EXCEPTION: email already exists");
        } else {
            EmployeeDto e = prs.save(employeeDTO).get();
//            PersonDTO e = employeeDTO; // temp deleteme
            return new ResponseEntity<EmployeeDto>(e, HttpStatus.OK);
        }
    }

    @PostMapping(path = ChangePass.PATH, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<String> changePassword(@Valid
//                                                      @PasswordLengthValidation
//                                                      @PasswordPolicyValidation
//                                                      @PasswordNonReusePolicyValidation
                                                 @RequestBody PasswordDto newPasswordDTO,
                                                 Principal principal) {
        System.err.println(" public ResponseEntity<EmployeeDto> changePassword( newPasswordDTO: " + newPasswordDTO.getCleartextNewPassword());

        Optional<EmployeeDto> updatedWithPassword = prs.update(principal, newPasswordDTO);
        // TODO : deal with Optional / get()
        return new ResponseEntity<>(
                ChangePass.responseBuilder(updatedWithPassword.get().getEmail()), HttpStatus.OK);
    }
}



