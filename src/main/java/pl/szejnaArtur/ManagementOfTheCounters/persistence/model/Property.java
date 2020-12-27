package pl.szejnaArtur.ManagementOfTheCounters.persistence.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long propertyId;

    @Column
    @Size(min = 3, message = "Nazwa musi składać się z przynajmniej trzech znaków.")
    private String name;

    @Column
    @NotBlank(message = "To pole jest obowiązkowe.")
    private String street;

    @Column
    @NotBlank(message = "To pole jest obowiązkowe.")
    private String houseNumber;

    @Column
    private String flatNumber;

    @Column
    @NotBlank(message = "To pole jest obowiązkowe.")
    private String city;

    @Column
    @Pattern(regexp = "^[0-9]{2}-[0-9]{3}$", message = "Wartość musi być w formie XX-XXX")
    private String postalCode;

    @Column
    @NotBlank(message = "To pole jest obowiązkowe.")
    private String state;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "property", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Counter> counters;

    public void updateProperty(Property property) {
        this.setName(property.getName());
        this.setStreet(property.getStreet());
        this.setHouseNumber(property.getHouseNumber());
        this.setFlatNumber(property.getFlatNumber());
        this.setCity(property.getCity());
        this.setPostalCode(property.getPostalCode());
//        this.setState(property.getState());
    }
}
