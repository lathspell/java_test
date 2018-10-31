package test3.repo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import test3.model.Author;

@Repository
public interface AuthorRepo extends JpaRepository<Author, Long> {

}
