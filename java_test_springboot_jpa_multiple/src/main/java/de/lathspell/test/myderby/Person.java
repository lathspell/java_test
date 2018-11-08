package de.lathspell.test.myderby;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    // @ManyToOne(/* fetch=LAZY, cascade = CascadeType.ALL, optional = true, targetEntity = Team.class */)
    // @Column(name = "team_id")
    //private Team team;

}
