package de.lathspell.test.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pets")
public class Pet extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;
}
