package account.service;

import account.entity.PersonDTO;
import account.repository.PasswordDTORepository;
import account.security.entity.PasswordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PasswordDTORepositoryService {

    @Autowired
    private PasswordDTORepository passwordDTORepository;

    PersonDTO findByPerson(PersonDTO personDTO) {
//        PasswordDTO p = per.findByPerson(personDTO).orElseThrow();
        return null;

    }

    PersonDTO findByUserName(PersonDTO personDTO) {
return null;
    }

    PersonDTO findByEmail(PersonDTO personDTO) {
        return null;

    }

    PersonDTO findByPrincipal(PersonDTO personDTO) {
        return null;

    }

    PasswordDTO save(PasswordDTO passwordDTO) {
        // validate passwordDTO
        return passwordDTORepository.save(passwordDTO);
    }
}
