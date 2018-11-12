package test5.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import test5.model.Author;

public interface AuthorRepo extends JpaRepository<Author, Long>, MyExtendedRepository<Author, Long> {

    public Author findByName(String name);

}
