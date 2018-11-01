package test3.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import test3.model.Book;

import java.util.List;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {

    Book findByTitle(String title);

    List<Book> findBooksByState(Book.State state);

    /**
     * This custom method calls the NamedQuery of the same name which is defined in class Book.
     */
    List<Book> findBooksByTitleLenCustom(@Param("len") int len);

    /** Same Query but this time declared in the Repository. */
    @Query("SELECT b FROM Book b WHERE length(b.title) = :len")
    List<Book> findBooksByTitleLen2Custom(@Param("len") int len);
}
