package account.service;

import account.entity.PersonDto;
import account.repository.PasswordRepository;
import account.security.entity.PasswordDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

@Service
//@Transactional(Transactional.TxType.NEVER)
public class PasswordRepositoryService {

    @Autowired
    private PasswordRepository passwordRepository;

    PersonDto findByPerson(PersonDto personDTO) {
//        PasswordDTO p = per.findByPerson(personDTO).orElseThrow();
        return null;

    }

    PersonDto findByUserName(PersonDto personDTO) {
return null;
    }

    PersonDto findByEmail(PersonDto personDTO) {
        return null;

    }

    PersonDto findByPrincipal(PersonDto personDTO) {
        return null;

    }

    PasswordDto save(PasswordDto passwordDTO) {
        // validate passwordDTO
        try {
            return passwordRepository.saveAndFlush(passwordDTO);
        } catch (ConstraintViolationException ex) {
            throw ex;
        } catch (TransactionSystemException tex) {
            throw new TransactionSystemException("DEBUG EXCEPTION TYPE TransactionSystemExceotion in PasswordRepositoryService");
        }
    }
}
