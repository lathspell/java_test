package de.lathspell.java_test_ee6_querydsl.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "book_to_bookstore")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BookToBookstore.findAll", query = "SELECT b FROM BookToBookstore b"),
    @NamedQuery(name = "BookToBookstore.findById", query = "SELECT b FROM BookToBookstore b WHERE b.id = :id"),
    @NamedQuery(name = "BookToBookstore.findByStock", query = "SELECT b FROM BookToBookstore b WHERE b.stock = :stock"),
    @NamedQuery(name = "BookToBookstore.findByCreatedAt", query = "SELECT b FROM BookToBookstore b WHERE b.createdAt = :createdAt"),
    @NamedQuery(name = "BookToBookstore.findByUpdatedAt", query = "SELECT b FROM BookToBookstore b WHERE b.updatedAt = :updatedAt")})
public class BookToBookstore implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "stock")
    private int stock;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_at", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "updated_at", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @JoinColumn(name = "bookstore_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Bookstore bookstoreId;
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Book bookId;

    public BookToBookstore() {
    }

    public BookToBookstore(Integer id) {
        this.id = id;
    }

    public BookToBookstore(Integer id, int stock, Date createdAt, Date updatedAt) {
        this.id = id;
        this.stock = stock;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Bookstore getBookstoreId() {
        return bookstoreId;
    }

    public void setBookstoreId(Bookstore bookstoreId) {
        this.bookstoreId = bookstoreId;
    }

    public Book getBookId() {
        return bookId;
    }

    public void setBookId(Book bookId) {
        this.bookId = bookId;
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
        if (!(object instanceof BookToBookstore)) {
            return false;
        }
        BookToBookstore other = (BookToBookstore) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.lathspell.java_test_jpa.model.BookToBookstore[ id=" + id + " ]";
    }

}
