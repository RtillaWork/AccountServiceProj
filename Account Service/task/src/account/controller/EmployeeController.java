package account.controller;

import account.entity.PersonDTO;
import account.route.v1.Payment;
import account.service.PersonRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@Validated
public class EmployeeController {

    @Autowired
    PersonRepositoryService prs;

//    @GetMapping(path = Payment.PATH)
//    @ResponseBody
//    public PersonDTO getEmployeePayment(Principal principal) {
////        Optional<Person> employee = prs.findByUsername(principal.getName());
//        PersonDTO p = prs.findByPrincipal(principal).orElseThrow();
//        return p;
//    }
}
