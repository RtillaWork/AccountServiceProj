package account.controller;

import account.service.PersonRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

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
