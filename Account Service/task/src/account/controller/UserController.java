package account.controller;

import account.entity.Person;
//import account.exception.UserAlreadyExistsException;
import account.route.v1.ChangePass;
import account.route.v1.Signup;
import account.security.entity.PasswordEntity;
import account.service.PersonRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.security.InvalidParameterException;
import java.security.Principal;
import java.util.Optional;

@RestController
@Validated
public class UserController {

    @Autowired
    PersonRepositoryService prs;

    @Autowired
    PasswordEntity passwordEntity;

    @PostMapping(path = Signup.PATH)
    @ResponseBody
    public ResponseEntity<Person> signup(@Valid @RequestBody Person person) {
        if (person == null) {
            throw new ValidationException("EXCEPTION: person object is null");
        } else  if (prs.findByEmail(person).isPresent()) {
            throw new DataIntegrityViolationException("EXCEPTION: email already exists");
        }
        else {
            Person p = prs.save(person).orElseThrow();
            return new ResponseEntity<Person>(p, HttpStatus.OK);
        }
    }

    @PostMapping(path = ChangePass.PATH)
    @ResponseBody
    public ResponseEntity<Person> changePassword(@Valid @RequestBody PasswordEntity newPasswordEntity,
                                                 Principal principal) {
        Person p =  prs.updatePassword(principal, newPasswordEntity).orElseThrow();
        return new ResponseEntity<>(p, HttpStatus.OK);


    }
}



