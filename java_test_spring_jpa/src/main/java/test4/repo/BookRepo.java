package test4.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import test4.model.Book;

import java.util.List;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {

    Book findByTitle(String title);

    List<Book> findBooksByState(Book.State state);

    /**
     * This custom method calls the NamedQuery of the same name which is defined in class Book.
     */
    List<Book> findBooksByTitleLenCustom(@Param("len") int len);
}
