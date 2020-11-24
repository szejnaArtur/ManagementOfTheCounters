package pl.szejnaArtur.ManagementOfTheCounters.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
public class Counter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long counterId;

    @Column
    private String name;

    @Column
    private String room;

    @Column
    private String unit;

    @Column
    private Double price;

    @Column
    private String billingPeriod;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "property_id")
    private Property property;
}


