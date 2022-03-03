package account.service;

import account.entity.PersonDto;
import account.exception.PasswordRequirementException;
import account.repository.PasswordRepository;
import account.security.entity.PasswordDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.security.Principal;

@Service
//@Transactional(Transactional.TxType.NEVER)
public class PasswordRepositoryService {

    @Autowired
    private PasswordRepository passwordRepository;

//
//    PersonDto findByPerson(PersonDto personDTO) {
////        PasswordDTO p = per.findByPerson(personDTO).orElseThrow();
//        return null;
//
//    }
//
//    PersonDto findByUserName(PersonDto personDTO) {
//return null;
//    }
//
//    PersonDto findByEmail(PersonDto personDTO) {
//        return null;
//
//    }

    PersonDto findByPrincipal(Principal principal) {
        String username = principal.getName();
        return null;

    }

    public PasswordDto save( PasswordDto passwordDTO) { // throws PasswordRequirementException {

        PasswordDto password = passwordRepository.save(passwordDTO);
        return password;
//        // validate passwordDTO
//        try {
//            return passwordRepository.saveAndFlush(passwordDTO);
//        } catch (ConstraintViolationException ex) {
//            throw ex;
//        } catch (TransactionSystemException tex) {
//            var ex= tex.getApplicationException();
//            throw new PasswordRequirementException(
//                    "DEBUG EXCEPTION TYPE TransactionSystemExceotion in PasswordRepositoryService",
//                    ex);
////            throw new TransactionSystemException("DEBUG EXCEPTION TYPE TransactionSystemExceotion in PasswordRepositoryService");
//        }
    }

//    public PasswordDto update(PasswordDto passwordDTO, String cleartextPassword) { // throws PasswordRequirementException {
//        passwordDTO.setHashedPassword(cleartextPassword);
//        System.out.println("from passwordDto service SAVED passwordDTO.setHashedPassword(cleartextPassword) cleartext: " + cleartextPassword);
//        PasswordDto password = passwordRepository.save(passwordDTO);
//        return password;
////        // validate passwordDTO
////        try {
////            return passwordRepository.saveAndFlush(passwordDTO);
////        } catch (ConstraintViolationException ex) {
////            throw ex;
////        } catch (TransactionSystemException tex) {
////            var ex= tex.getApplicationException();
////            throw new PasswordRequirementException(
////                    "DEBUG EXCEPTION TYPE TransactionSystemExceotion in PasswordRepositoryService",
////                    ex);
//////            throw new TransactionSystemException("DEBUG EXCEPTION TYPE TransactionSystemExceotion in PasswordRepositoryService");
////        }
//    }
}
