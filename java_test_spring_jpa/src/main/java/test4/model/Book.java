package test4.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries(
        @NamedQuery(name = "Book.findBooksByTitleLenCustom", query = "SELECT b FROM Book b WHERE length(b.title) = :len")
)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull
    private String title;

    @Column(nullable = false, length = 12)
    @NotNull
    @Size(min = 12, max = 12, message = "ISBN must be 12 digits long!")
    private String isbn;

    @Column
    @NotNull
    private State state;

    @Column(name = "published_at", nullable = false)
    @NotNull
    private LocalDate publishedAt;

    @ManyToOne(cascade = {}, fetch = FetchType.EAGER, targetEntity = Author.class, optional = false)
    private Author author;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    public static enum State {
        NEW, REVIEW, PUBLISHED;
    }

    @PrePersist
    public void postConstruct() {
        if (updatedAt == null) {
            updatedAt = LocalDate.now();
        }
    }
}
