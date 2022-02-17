package account.controller;

import account.entity.User;
import account.route.v1.ChangePass;
import account.route.v1.Signup;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<User> signup(@Valid @RequestBody User user) {
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


}
