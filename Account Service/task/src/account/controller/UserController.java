package account.controller;

import account.entity.Person;
import account.route.v1.Signup;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Validated
public class UserController {



    @PostMapping(path = Signup.PATH)
    public ResponseEntity<Person> signup(@Valid @RequestBody Person person) {
        return new ResponseEntity<>(person, HttpStatus.OK);
    }


}
