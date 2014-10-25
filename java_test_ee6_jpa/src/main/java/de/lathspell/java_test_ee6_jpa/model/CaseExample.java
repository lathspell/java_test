package de.lathspell.java_test_ee6_jpa.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class CaseExample implements Serializable {

    @Id
    Long id;

    @Basic
    String fooBar;

}
