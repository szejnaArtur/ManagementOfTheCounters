package pl.szejnaArtur.ManagementOfTheCounters.entity.repository;

import org.springframework.data.repository.CrudRepository;
import pl.szejnaArtur.ManagementOfTheCounters.entity.Role;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Optional<Role> findByName(String name);

}
