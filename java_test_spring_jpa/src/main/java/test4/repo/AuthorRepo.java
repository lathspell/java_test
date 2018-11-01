package test4.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test4.model.Author;

@Repository
public interface AuthorRepo extends JpaRepository<Author, Long> {

}
