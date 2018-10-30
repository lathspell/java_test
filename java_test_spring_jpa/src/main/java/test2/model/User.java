package test2.model;

import de.lathspell.test.model.AbstractEntity;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class User extends AbstractEntity {

    @NotEmpty
    @Column(nullable = false, unique = true)
    private String username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    private UserType userType;

    @NotEmpty
    @Column(unique = true)
    private String email;

    @OneToMany(fetch = EAGER, cascade = CascadeType.ALL)
    private final Set<Pet> pets = new HashSet<>();
}
