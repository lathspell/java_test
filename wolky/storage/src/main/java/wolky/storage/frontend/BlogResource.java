package wolky.storage.frontend;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import wolky.storage.model.BlogEntry;
import wolky.storage.repo.BlogRepo;

@RestController
public class BlogResource {

    @Autowired
    private BlogRepo repo;

    @GetMapping(path = "/rest/blog/entries", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BlogEntry> getAllEntries() {
        return repo.findAll();
    }

    @GetMapping(path = "/rest/blog/entries/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BlogEntry getEntry(@PathVariable("id") long id) {
        return repo.getOne(id);
    }

}
