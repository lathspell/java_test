package de.lathspell.java_test_ee7_jpa.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.Table;

import static javax.persistence.InheritanceType.*;

import static de.lathspell.java_test_ee7_jpa.model.EnumExample.MyEnum.*;

@Entity
@Table(name = "enum_example")
@Inheritance(strategy = TABLE_PER_CLASS) // Do not specifiy this on the child table!
public class EnumExample implements Serializable {

    public static enum MyEnum {

        RED, GREEN, BLUE;

    }
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Basic
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "as_int", nullable = false)
    MyEnum asInt = RED;

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "as_string")
    MyEnum asString = RED;

}
