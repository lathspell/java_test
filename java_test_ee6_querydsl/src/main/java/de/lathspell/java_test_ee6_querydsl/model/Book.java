package de.lathspell.java_test_ee6_querydsl.model;

import java.io.Serializable;
import java.util.List;
import java.util.Date;
import java.util.List;

import javax.annotation.Generated;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "books")
@XmlRootElement
public class Book implements Serializable {

    public static enum Status {

        IN_STOCK,
        SOLD_OUT;
    }
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Column(name = "published_in")
    @Temporal(TemporalType.DATE)
    private Date publishedIn;
    @Basic(optional = false)
    @NotNull
    @Column(name = "status", nullable = false, columnDefinition = "enum('IN_STOCK', 'SOLD_OUT') default 'IN_STOCK' not null")
    @Enumerated(EnumType.STRING)
    private Status status;
    @JoinColumn(name = "language_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Language languageId;
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Author authorId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bookId")
    private List<BookToBookstore> bookToBookstoreList;

    /**
     * JPA is not able to skip this column on INSERT so that the database
     * default value could get assigned.
     */
    @PrePersist
    public void prePersist() {
        if (status == null) {
            status = Status.IN_STOCK;
        }
    }

    public Book() {
    }

    public Book(Integer id) {
        this.id = id;
    }

    public Book(Integer id, String title, Date publishedIn, Status status) {
        this.id = id;
        this.title = title;
        this.publishedIn = publishedIn;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getPublishedIn() {
        return publishedIn;
    }

    public void setPublishedIn(Date publishedIn) {
        this.publishedIn = publishedIn;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Language getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Language languageId) {
        this.languageId = languageId;
    }

    public Author getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Author authorId) {
        this.authorId = authorId;
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
        if (!(object instanceof Book)) {
            return false;
        }
        Book other = (Book) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.lathspell.java_test_jpa.model.Book[ id=" + id + " ]";
    }
}
