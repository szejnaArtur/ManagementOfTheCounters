package pl.szejnaArtur.ManagementOfTheCounters.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import pl.szejnaArtur.ManagementOfTheCounters.persistence.model.Role;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Optional<Role> findByName(String name);

}
