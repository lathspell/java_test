package wolky.storage.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wolky.storage.model.BlogEntry;

@Repository
public interface BlogRepo extends JpaRepository<BlogEntry, Long> {

}
