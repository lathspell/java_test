package test2.model;

import de.lathspell.test.model.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "pets")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Pet extends AbstractEntity {

    @Column
    private String name;
}
