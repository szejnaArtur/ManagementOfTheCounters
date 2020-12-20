package pl.szejnaArtur.ManagementOfTheCounters.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import pl.szejnaArtur.ManagementOfTheCounters.persistence.model.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByConfirmationToken(String token);

}
