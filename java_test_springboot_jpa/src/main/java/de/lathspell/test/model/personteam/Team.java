package de.lathspell.test.model.personteam;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Entity
@Data
@NoArgsConstructor
public class Team {

    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(/* mappedBy = "team", cascade = ALL, fetch = FetchType.EAGER, orphanRemoval = true, targetEntity = Person.class */)
    @JoinColumn(name = "team_id")
    private List<Person> persons = new ArrayList<>();

    public Team(String name) {
        this.name = name;
    }

}
