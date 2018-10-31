package test10;

import common.model.Book;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import test10.config.Test10Config;
import test10.repos.BookRepository;

import java.time.LocalDate;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Test10Config.class)
@Slf4j
public class RepoTest {

    @Autowired
    private BookRepository repo;


    @Test
    public void testCrud() throws Exception {
        LocalDate now = LocalDate.now();

        assertNull(repo.findById(0));

        Book book1 = new Book(null, "BigBook", "12345678", now);
        Book book1b = repo.save(book1);

        assertNotNull(book1b.getId());
        assertEquals("BigBook", book1b.getName());

        Book book1c = repo.findById(book1b.getId());
        assertEquals("BigBook", book1b.getName());
        assertEquals(now, book1c.getPublished());
    }
}
