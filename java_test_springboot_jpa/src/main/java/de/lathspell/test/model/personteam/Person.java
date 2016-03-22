package de.lathspell.test.model.personteam;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

import static javax.persistence.FetchType.LAZY;

@Entity
@Data
@NoArgsConstructor
public class Person implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    private int yearOfBirth;

    @ManyToOne(/* fetch=LAZY, cascade = CascadeType.ALL, optional = true, targetEntity = Team.class */)
    // @Column(name = "team_id")
    private Team team;

    public Person(String firstName, String lastName, int yearOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.yearOfBirth = yearOfBirth;
    }

}
