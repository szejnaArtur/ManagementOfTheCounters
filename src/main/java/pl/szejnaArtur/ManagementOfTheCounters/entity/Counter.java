package pl.szejnaArtur.ManagementOfTheCounters.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

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
    private String unit;

    @Column
    private Double price;

    @Column
    private Integer billingPeriod;

    @Column
    private String firstBillingPeriod;

    @Column
    private Double initialState;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "property_id")
    private Property property;

    @OneToMany(mappedBy = "counter", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<MeterStatus> meterStatutes;

    public static Counter of(String name, String unit, Double price, Integer billingPeriod, String firstBillingPeriod,
                             Double initialState, Property property){
        Counter counter = new Counter();
        counter.setName(name);
        counter.setUnit(unit);
        counter.setPrice(price);
        counter.setBillingPeriod(billingPeriod);
        counter.setFirstBillingPeriod(firstBillingPeriod);
        counter.setInitialState(initialState);
        counter.setProperty(property);
        return counter;
    }


}


