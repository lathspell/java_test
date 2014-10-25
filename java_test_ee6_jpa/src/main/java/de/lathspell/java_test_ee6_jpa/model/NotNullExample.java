package de.lathspell.java_test_ee6_jpa.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * How to define SQL "NOT NULL" columns?
 *
 * @Basic(optional=true)
 * Defines whether the value of the field or property may be null. This is a
 * hint and is disregarded for primitive types; it may be used in schema
 * generation. If not specified, defaults to true.
 *
 * @NotNull
 * The annotated element must not be null. Accepts any type.
 * http://docs.oracle.com/javaee/6/api/javax/validation/constraints/NotNull.html
 * => Leads to ConstraintViolationException at em.persist()
 * => Leads to NOT NULL
 *
 * @Column(nullable = false)
 * Whether the database column is nullable.
 * => Leads to MySQLIntegrityConstraintViolationException at em.persist()
 *
 * @author james
 */
@Entity
@Table(name = "not_null_example")
public class NotNullExample implements Serializable {

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Basic(optional = false)
    String a;

    @Basic
    @NotNull
    String b;

    @Basic
    @Column(nullable = false)
    String c;

    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    String d;
}
