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
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long propertyId;

    @Column
    private String name;

    @Column
    private String street;

    @Column
    private String houseNumber;

    @Column
    private String flatNumber;

    @Column
    private String city;

    @Column
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
