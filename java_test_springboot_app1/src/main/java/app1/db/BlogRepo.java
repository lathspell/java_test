package app1.db;

import app1.model.BlogEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepo extends JpaRepository<BlogEntry, Long> {

}
