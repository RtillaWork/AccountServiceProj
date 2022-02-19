package account.controller;

import account.entity.Person;
import account.exception.UserAlreadyExistsException;
import account.route.v1.Signup;
import account.service.PersonRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.InvalidParameterException;
import java.util.Optional;

@RestController
@Validated
public class UserController {

    @Autowired
    PersonRepositoryService prs;

    @PostMapping(path = Signup.PATH)
    @ResponseBody
    public ResponseEntity<Person> signup(@Valid @RequestBody Person person) {
        if (person == null) {
            throw new InvalidParameterException("EXCEPTION: Person person object is null");
        } else if (prs.findByEmail(person).isPresent()) {
            throw new UserAlreadyExistsException();
        } else {
            Person p = prs.save(person).orElseThrow();
            return new ResponseEntity<Person>(p, HttpStatus.OK);
        }
    }
}



