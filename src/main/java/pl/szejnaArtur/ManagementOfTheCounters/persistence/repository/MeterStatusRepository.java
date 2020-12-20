package pl.szejnaArtur.ManagementOfTheCounters.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.szejnaArtur.ManagementOfTheCounters.persistence.model.MeterStatus;

public interface MeterStatusRepository extends JpaRepository<MeterStatus, Long> {



}
