package test5;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import test5.config.Test5Config;
import test5.model.Author;
import test5.repo.AuthorRepo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Test5Config.class)
@Slf4j
public class RepoTest {

    @Autowired
    private AuthorRepo authorRepo;

    @Test
    public void testCrud() {

        assertFalse(authorRepo.findById(42L).isPresent());

        Author author1 = new Author(null, "Ann");
        authorRepo.save(author1);
        assertFalse(authorRepo.findById(42L).isPresent());

        Author author2 = new Author(null, "Ann");
        authorRepo.saveWithGivenId(42L, author2);
        assertTrue(authorRepo.findById(42L).isPresent());

        assertEquals(2, authorRepo.findByName("Ann"));
    }
}
