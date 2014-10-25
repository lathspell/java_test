package de.lathspell.java_test_ee7_jpa.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import static de.lathspell.java_test_ee7_jpa.model.EnumExample.MyEnum.*;

@Entity
@Table(name = "mysql_enum_example")
public class EnumExampleMysql extends EnumExample {

    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    // Beware that "nullable = false" has no effect if "columnDefinition" is used!
    @Column(name = "as_enum", columnDefinition = "enum('RED', 'GREEN', 'BLUE') default 'RED' not null")
    MyEnum asEnum = RED;

}
