package wolky.storage.init;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wolky.storage.model.BlogEntry;
import wolky.storage.repo.BlogRepo;
import wolky.storage.storage.repo.BlogTagRepo;
import wolky.storage.storage.model.BlogTag;

@Component
public class DbLoad {

    @Autowired
    private BlogTagRepo blogTagRepo;

    @Autowired
    private BlogRepo blogRepo;

    @PostConstruct
    public void postConstruct() {
        BlogTag newsTag = new BlogTag(null, "news");
        blogTagRepo.save(newsTag);

        BlogEntry entry = new BlogEntry(null, "First Entry", "Hello World", Arrays.asList(newsTag), LocalDateTime.now());
        blogRepo.save(entry);
    }
}
