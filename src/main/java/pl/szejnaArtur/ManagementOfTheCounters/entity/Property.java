package pl.szejnaArtur.ManagementOfTheCounters.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
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
    @NotBlank(message = "To pole jest obowiązkowe.")
    private String postalCode;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "property", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Counter> counters;

    public static Property of(String name, String street, String houseNumber, String flatNumber, String postalCode,
                              String city, User user) {
        Property property = new Property();
        property.setName(name);
        property.setStreet(street);
        if (!houseNumber.isEmpty()) property.setHouseNumber(houseNumber);
        if (!flatNumber.isEmpty()) property.setFlatNumber(flatNumber);
        if (!postalCode.isEmpty()) property.setPostalCode(postalCode);
        property.setCity(city);
        property.setUser(user);

        return property;
    }
}
