package account.repository;

import account.security.entity.PasswordDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordRepository extends JpaRepository<PasswordDto, Long> {

    Optional<PasswordDto> findById(Long aLong);
//    Optional<PasswordDTO> findByPersonDTO(PersonDTO personDTO);

    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param entity must not be {@literal null}.
     * @return the saved entity; will never be {@literal null}.
     * @throws IllegalArgumentException in case the given {@literal entity} is {@literal null}.
     */
    @Override
    <S extends PasswordDto> S save(S entity);
}
