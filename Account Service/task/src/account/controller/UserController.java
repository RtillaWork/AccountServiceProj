package account.controller;

import account.entity.Person;
import account.route.v1.Signup;
import account.service.PersonRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@Validated
public class UserController {

    @Autowired
    PersonRepositoryService prs;

    @PostMapping(path = Signup.PATH)
    @ResponseStatus(code = HttpStatus.I_AM_A_TEAPOT, reason = "I'm a Teatpot indeed!")
    public ResponseEntity<Person> signup(@Valid @RequestBody Person person) {

        Optional<Person> p = prs.save(person);

        if (p.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(person, HttpStatus.OK);
        }
    }
}
