package test3;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import test3.config.Test3Config;
import test3.model.Author;
import test3.model.Book;
import test3.repo.AuthorRepo;
import test3.repo.BookRepo;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Test3Config.class)
@Slf4j
public class RepoTest {

    @Autowired
    private AuthorRepo authorRepo;

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    private final ObjectMapper om = new ObjectMapper().registerModule(new JavaTimeModule());

    @PostConstruct
    public void postConstruct() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Test
    public void testCustomFinders() {
        Author author1 = new Author(null, "Ann");
        Book book1 = new Book(null, "Ann's Book", "123456789012", Book.State.NEW, LocalDate.now(), author1, null);
        authorRepo.saveAndFlush(author1);
        bookRepo.saveAndFlush(book1);

        List<Book> found = bookRepo.findBooksByState(Book.State.NEW);
        assertEquals(1, found.size());
    }

    @Test
    public void testCrud() {
        // Save
        Author author1 = new Author(null, "Ann");
        Book book1 = new Book(null, "Ann's Book", "123456789012", Book.State.NEW, LocalDate.now(), author1, null);
        authorRepo.saveAndFlush(author1);
        bookRepo.saveAndFlush(book1);

        // Check auto increment ids
        assertNotNull(book1.getId());

        // Refresh
        Book book1b = bookRepo.findByName("Ann's Book"); // custom finder
        Author author1b = book1b.getAuthor();

        assertNotNull(book1b.getId());
        assertEquals(LocalDate.now().toEpochDay(), book1b.getUpdatedAt().toEpochDay());
        assertNotNull(author1b.getId());
        assertEquals(book1b.getAuthor().getId(), author1b.getId());

        // Delete book but not author
        assertEquals(1, authorRepo.count());
        assertEquals(1, bookRepo.count());
        bookRepo.delete(book1);
        assertEquals(1, authorRepo.count());
        assertEquals(0, bookRepo.count());
    }
}
