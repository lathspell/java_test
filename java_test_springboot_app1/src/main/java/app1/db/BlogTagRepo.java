package app1.db;

import app1.model.BlogTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogTagRepo extends JpaRepository<BlogTag, Long> {

}
