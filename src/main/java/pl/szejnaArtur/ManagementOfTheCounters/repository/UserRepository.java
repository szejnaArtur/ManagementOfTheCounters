package pl.szejnaArtur.ManagementOfTheCounters.repository;

import org.springframework.data.repository.CrudRepository;
import pl.szejnaArtur.ManagementOfTheCounters.entity.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByConfirmationToken(String token);

}
