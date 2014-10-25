package de.lathspell.java_test_ee6_jpa.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

// See README
import org.eclipse.persistence.annotations.CascadeOnDelete;

@Entity
@Table(name = "bookstores")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bookstore.findAll", query = "SELECT b FROM Bookstore b"),
    @NamedQuery(name = "Bookstore.findById", query = "SELECT b FROM Bookstore b WHERE b.id = :id"),
    @NamedQuery(name = "Bookstore.findByName", query = "SELECT b FROM Bookstore b WHERE b.name = :name"),
    @NamedQuery(name = "Bookstore.findByCategory", query = "SELECT b FROM Bookstore b WHERE b.category = :category")})
public class Bookstore implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "category")
    private short category;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bookstoreId")
    @CascadeOnDelete
    private List<BookToBookstore> bookToBookstoreList;

    public Bookstore() {
    }

    public Bookstore(Integer id) {
        this.id = id;
    }

    public Bookstore(Integer id, String name, short category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getCategory() {
        return category;
    }

    public void setCategory(short category) {
        this.category = category;
    }

    @XmlTransient
    public List<BookToBookstore> getBookToBookstoreList() {
        return bookToBookstoreList;
    }

    public void setBookToBookstoreList(List<BookToBookstore> bookToBookstoreList) {
        this.bookToBookstoreList = bookToBookstoreList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bookstore)) {
            return false;
        }
        Bookstore other = (Bookstore) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.lathspell.java_test_jpa.model.Bookstore[ id=" + id + " ]";
    }
}
