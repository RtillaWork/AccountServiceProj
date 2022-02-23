package account.repository;

import account.entity.PersonDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<PersonDTO, Long> {
    Optional<PersonDTO> findById(Long aLong);
    Optional<PersonDTO> findByEmail(String email);
}
