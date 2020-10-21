package pl.szejnaArtur.ManagementOfTheCounters.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.szejnaArtur.ManagementOfTheCounters.entity.Counter;

import java.util.Optional;

public interface CounterRepository extends JpaRepository<Counter, Long> {

    Optional<Counter> findByCounterId(Long id);

}
