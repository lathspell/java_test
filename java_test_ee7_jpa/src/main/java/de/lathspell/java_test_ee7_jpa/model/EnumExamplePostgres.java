package de.lathspell.java_test_ee7_jpa.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

import static de.lathspell.java_test_ee7_jpa.model.EnumExample.MyEnum.*;

/**
 *
 * Prepare with:
 * <pre>
 *   CREATE TYPE my_enum AS ENUM ('RED', 'GREEN', 'BLUE');
 * </pre>
 */
@Entity
@Table(name = "postgres_enum_example")
public class EnumExamplePostgres extends EnumExample {

    @Basic(optional = false)
    // Beware that "nullable = false" has no effect if "columnDefinition" is used!
    @Column(name = "as_enum", columnDefinition = "my_enum default 'RED' not null")
    @Convert(converter = MyEnumPostgresConverter.class)
    MyEnum asEnum = RED;

}
