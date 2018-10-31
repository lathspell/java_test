package test3.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test3.model.Book;

import java.util.List;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {

    public Book findByName(String name);

    public List<Book> findBooksByState(Book.State state);
}
