package account.repository;

import account.entity.PersonDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<PersonDto, Long> {
    Optional<PersonDto> findById(Long aLong);
    Optional<PersonDto> findByEmail(String email);
}
