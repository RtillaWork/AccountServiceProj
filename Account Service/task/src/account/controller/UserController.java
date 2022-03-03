package account.controller;

import account.entity.PersonDto;
//import account.exception.UserAlreadyExistsException;
import account.route.v1.ChangePass;
import account.route.v1.Signup;
import account.security.entity.PasswordDto;
import account.service.PersonRepositoryService;
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
    PersonRepositoryService prs;


    @PostMapping(path = Signup.PATH)
    @ResponseBody
    public ResponseEntity<PersonDto> signup(@Valid @RequestBody PersonDto personDTO) {
        if (personDTO == null) {
            throw new ValidationException("EXCEPTION: person object is null");
        } else  if (prs.findByEmail(personDTO).isPresent()) {
            throw new DataIntegrityViolationException("EXCEPTION: email already exists");
        }
        else {
            PersonDto p = prs.save(personDTO);
//            PersonDTO p = personDTO; // temp deleteme
            return new ResponseEntity<PersonDto>(p, HttpStatus.OK);
        }
    }

    @PostMapping(path = ChangePass.PATH)
    @ResponseBody
    public ResponseEntity<PersonDto> changePassword(@Valid @RequestBody PasswordDto newPasswordDTO,
                                                    Principal principal) {
        System.out.println(" public ResponseEntity<PersonDto> changePassword( newPasswordDTO: " + newPasswordDTO.getClearTextPassword());
        PersonDto p =  prs.update(principal, newPasswordDTO);
        return new ResponseEntity<>(p, HttpStatus.OK);


    }
}



