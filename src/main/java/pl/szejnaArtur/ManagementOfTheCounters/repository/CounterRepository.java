package pl.szejnaArtur.ManagementOfTheCounters.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.szejnaArtur.ManagementOfTheCounters.entity.Counter;
import pl.szejnaArtur.ManagementOfTheCounters.entity.Property;

import java.util.List;

public interface CounterRepository extends JpaRepository<Counter, Long> {

    List<Counter> findAllByProperty(Property property);

    Counter findByCounterId(Long id);

}
