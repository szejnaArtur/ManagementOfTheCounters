package pl.szejnaArtur.ManagementOfTheCounters.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.szejnaArtur.ManagementOfTheCounters.persistence.model.Counter;
import pl.szejnaArtur.ManagementOfTheCounters.persistence.model.Property;

import java.util.List;

public interface CounterRepository extends JpaRepository<Counter, Long> {

    List<Counter> findAllByProperty(Property property);

    Counter findByCounterId(Long id);

}
