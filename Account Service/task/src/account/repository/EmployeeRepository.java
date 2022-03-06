package account.repository;

import account.entity.EmployeeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeDto, Long> {
    Optional<EmployeeDto> findById(Long aLong);
    Optional<EmployeeDto> findByEmail(String email);

    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param entity must not be {@literal null}.
     * @return the saved entity; will never be {@literal null}.
     * @throws IllegalArgumentException in case the given {@literal entity} is {@literal null}.
     */
    @Override
    <S extends EmployeeDto> S save(S entity);


}