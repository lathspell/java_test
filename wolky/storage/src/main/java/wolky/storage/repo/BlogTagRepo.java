package wolky.storage.storage.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wolky.storage.storage.model.BlogTag;

@Repository
public interface BlogTagRepo extends JpaRepository<BlogTag, Long> {

}
