package pl.szejnaArtur.ManagementOfTheCounters.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRole;

    @Column
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}
