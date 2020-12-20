package pl.szejnaArtur.ManagementOfTheCounters.persistence.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@ToString
public class MeterStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long meter_status_id;

    @Column
    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
    private LocalDate date;

    @Column
    @NotNull(message = "To pole jest obowiązkowe.")
    private Double status;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "counterId")
    private Counter counter;


}
