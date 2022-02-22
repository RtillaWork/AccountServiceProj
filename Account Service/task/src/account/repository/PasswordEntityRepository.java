package account.repository;

import account.entity.Person;
import account.security.entity.PasswordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordEntityRepository extends JpaRepository<PasswordEntity, Long> {

    Optional<PasswordEntity> findById(Long aLong);
    Optional<PasswordEntity> findByPerson(Person person);

}
