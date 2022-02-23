package account.controller;

import account.entity.PersonDTO;
//import account.exception.UserAlreadyExistsException;
import account.route.v1.ChangePass;
import account.route.v1.Signup;
import account.security.entity.PasswordDTO;
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

    @Autowired
    PasswordDTO passwordDTO;

    @PostMapping(path = Signup.PATH)
    @ResponseBody
    public ResponseEntity<PersonDTO> signup(@Valid @RequestBody PersonDTO personDTO) {
        if (personDTO == null) {
            throw new ValidationException("EXCEPTION: person object is null");
        } else  if (prs.findByEmail(personDTO).isPresent()) {
            throw new DataIntegrityViolationException("EXCEPTION: email already exists");
        }
        else {
//            PersonDTO p = prs.save(personDTO).orElseThrow();
            PersonDTO p = personDTO; // temp deleteme
            return new ResponseEntity<PersonDTO>(p, HttpStatus.OK);
        }
    }

//    @PostMapping(path = ChangePass.PATH)
//    @ResponseBody
//    public ResponseEntity<PersonDTO> changePassword(@Valid @RequestBody PasswordDTO newPasswordDTO,
//                                                    Principal principal) {
//        PersonDTO p =  prs.updatePassword(principal, newPasswordDTO).orElseThrow();
//        return new ResponseEntity<>(p, HttpStatus.OK);
//
//
//    }
}



