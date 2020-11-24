package pl.szejnaArtur.ManagementOfTheCounters.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.szejnaArtur.ManagementOfTheCounters.entity.Property;
import pl.szejnaArtur.ManagementOfTheCounters.entity.User;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {

    List<Property> findAllByUser(User user);

    Property findByPropertyId(Long id);

}
