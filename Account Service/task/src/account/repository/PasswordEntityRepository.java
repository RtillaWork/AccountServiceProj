package account.repository;

import account.entity.PersonDTO;
import account.security.entity.PasswordDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordEntityRepository extends JpaRepository<PasswordDTO, Long> {

    Optional<PasswordDTO> findById(Long aLong);
    Optional<PasswordDTO> findByPerson(PersonDTO personDTO);

}
