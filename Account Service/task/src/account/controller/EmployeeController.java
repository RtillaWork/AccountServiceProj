package account.controller;

import account.entity.EmployeeDto;
import account.route.v1.Payment;
import account.service.EmployeeRepositoryService;
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
    EmployeeRepositoryService prs;

    @GetMapping(path = Payment.PATH)
    @ResponseBody
    public EmployeeDto getEmployeePayment(Principal principal) {
//        Optional<Person> employee = prs.findByUsername(principal.getName());
        EmployeeDto p = prs.findByPrincipal(principal).orElseThrow();
        return p;
    }
}
