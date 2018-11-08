package de.lathspell.test.myh2;

import de.lathspell.test.myderby.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

   // @OneToMany(/* mappedBy = "team", cascade = ALL, fetch = FetchType.EAGER, orphanRemoval = true, targetEntity = Person.class */)
    //@JoinColumn(name = "team_id")
    //private List<Person> persons = new ArrayList<>();

}
