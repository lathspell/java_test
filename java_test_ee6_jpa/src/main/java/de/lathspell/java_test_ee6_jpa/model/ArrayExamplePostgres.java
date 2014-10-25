package de.lathspell.java_test_ee6_jpa.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.annotations.Array;
import org.eclipse.persistence.annotations.Struct;

@Entity
@Table(name = "array_example" /* JPA2.1: indexes = @Index(name = "param_idx", columnList = ("params")) */)
@Struct(name = "params") // Else: [EclipseLink-157] "Normal descriptors do not support non-relational extensions."
@XmlRootElement
public class ArrayExamplePostgres implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private int id;

    @NotNull
    @Array(databaseType = "TEXT[]" /*, targetClass = String.class */) // Needs @Struct on class!
    @Column(name = "params")
    private List<String> params;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }

}
