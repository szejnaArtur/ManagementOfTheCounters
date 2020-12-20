package pl.szejnaArtur.ManagementOfTheCounters.persistence.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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
    @NotBlank(message = "To pole jest obowiązkowe.")
    private String name;

    @Column
    @NotBlank(message = "To pole jest obowiązkowe.")
    private String unit;

    @Column
    @NotBlank(message = "To pole jest obowiązkowe.")
    private String price;

    @Column
    @NotBlank(message = "To pole jest obowiązkowe.")
    private String billingPeriod;

    @Column
    @NotBlank(message = "To pole jest obowiązkowe.")
    private String firstBillingPeriod;

    @Column
    @NotBlank(message = "To pole jest obowiązkowe.")
    private String initialState;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "property_id")
    private Property property;

    @OneToMany(mappedBy = "counter", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<MeterStatus> meterStatutes;

    public void updateCounter(Counter counter){
        this.setName(counter.getName());
        this.setUnit(counter.getUnit());
        this.setPrice(counter.getPrice());
        this.setBillingPeriod(counter.getBillingPeriod());
        this.setFirstBillingPeriod(counter.getFirstBillingPeriod());
        this.setInitialState(counter.getInitialState());
    }

}


