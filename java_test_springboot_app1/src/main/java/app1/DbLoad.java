package app1;

import java.time.LocalDateTime;
import java.util.Arrays;

import javax.annotation.PostConstruct;

import app1.db.BlogRepo;
import app1.db.BlogTagRepo;
import app1.model.BlogEntry;
import app1.model.BlogTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
