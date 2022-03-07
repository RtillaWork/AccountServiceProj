package account.service;

import account.entity.EmployeeDto;
import account.repository.PasswordRepository;
import account.security.entity.PasswordDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.security.Principal;

@Service
//@Transactional(Transactional.TxType.NEVER)
public class PasswordRepositoryService {

    @Autowired
    private PasswordRepository passwordRepository;

    @Autowired
    Validator validator;

//
//    EmployeeDto findByPerson(EmployeeDto personDTO) {
////        PasswordDTO p = per.findByPerson(personDTO).orElseThrow();
//        return null;
//
//    }
//
//    EmployeeDto findByUserName(EmployeeDto personDTO) {
//return null;
//    }
//
//    EmployeeDto findByEmail(EmployeeDto personDTO) {
//        return null;
//
//    }

    EmployeeDto findByPrincipal(Principal principal) {
        String username = principal.getName();
        return null;

    }

//    public PasswordDto update(PasswordDto passwordDto, PasswordDto newPasswordDto) {
//        return update( passwordDto,  newPasswordDto.getCleartextNewPassword());
//    }
//
//    public PasswordDto update(PasswordDto passwordDto,String newCleartextPassword) {
//        passwordDto.setCleartextNewPassword(newCleartextPassword);
//        validator.validate(passwordDto)
//    }

    public PasswordDto save(PasswordDto passwordDTO) { // throws PasswordRequirementException {

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

    public void delete(PasswordDto passwordDto) {
        passwordRepository.delete(passwordDto);
    }
}
