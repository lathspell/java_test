package de.lathspell.java_test_ee6_nbpfcrud;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.bridge.SLF4JBridgeHandler;

import static org.junit.Assert.*;

import de.lathspell.java_test_ee6_nbpfcrud.model.Author;
import de.lathspell.java_test_ee6_nbpfcrud.model.Book;
import de.lathspell.java_test_ee6_nbpfcrud.model.BookToBookstore;
import de.lathspell.java_test_ee6_nbpfcrud.model.Bookstore;
import de.lathspell.java_test_ee6_nbpfcrud.model.Language;
import static de.lathspell.java_test_ee6_nbpfcrud.model.Book.Status.*;

public class BookstoreTest {

    private static final String PERSISTENCE_UNIT_NAME = "java_test_ee6_nbpfcrud_test_PU";
    private EntityManager em;
    private EntityTransaction et;
    private Language langDe;
    private Language langEn;
    private Author authorTwain;
    private Author authorVerne;
    private Bookstore bookstoreBlack;

    @Before
    public void before() throws ParseException {
        SLF4JBridgeHandler.install(); // for EclipseLink
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        assertNotNull(factory);
        em = factory.createEntityManager();
        assertNotNull(em);
        et = em.getTransaction();
        assertNotNull(et);

        fixtures();
    }

    @After
    public void after() {
        if (et.isActive()) {
            et.rollback(); // failed junit assertion
        }
        em.close();
    }

    @Test
    public void testDummy() {
    }

    private void fixtures() throws ParseException {
        et.begin();
        em.createNativeQuery("DELETE FROM book_to_bookstore").executeUpdate();
        em.createNativeQuery("DELETE FROM bookstores").executeUpdate();
        em.createNativeQuery("DELETE FROM books").executeUpdate();
        em.createNativeQuery("DELETE FROM authors").executeUpdate();
        em.createNativeQuery("DELETE FROM languages").executeUpdate();
        et.commit();

        et.begin();
        langDe = new Language();
        langDe.setCode("de");
        langDe.setName("Deutsch");
        em.persist(langDe);

        langEn = new Language();
        langEn.setCode("en");
        langEn.setName("English");
        em.persist(langEn);

        authorTwain = new Author();
        authorTwain.setFirstName("Mark");
        authorTwain.setLastName("Twain");
        em.persist(authorTwain);

        authorVerne = new Author();
        authorVerne.setFirstName("Jules");
        authorVerne.setLastName("Verne");
        em.persist(authorVerne);

        Book book1 = new Book();
        book1.setAuthorId(authorTwain);
        book1.setLanguageId(langEn);
        book1.setPublishedIn(new SimpleDateFormat("yyyy-MM-dd").parse("1990-01-02"));
        book1.setStatus(IN_STOCK); // No default value!
        book1.setTitle("The Awful German Language");
        em.persist(book1);

        Book book2 = new Book();
        book2.setAuthorId(authorTwain);
        book2.setLanguageId(langDe);
        book1.setPublishedIn(new SimpleDateFormat("yyyy-MM-dd").parse("1980-01-02"));
        book2.setStatus(IN_STOCK);
        book2.setTitle("Tom Sawyer");
        em.persist(book2);

        Book book3 = new Book();
        book3.setAuthorId(authorVerne);
        book3.setLanguageId(langDe);
        book3.setStatus(IN_STOCK);
        book3.setTitle("20.000 Meilen unter dem Meer");
        em.persist(book3);

        bookstoreBlack = new Bookstore();
        bookstoreBlack.setName("MyBookstore");
        em.persist(bookstoreBlack);

        BookToBookstore b2b = new BookToBookstore();
        b2b.setBookId(book2);
        b2b.setBookstoreId(bookstoreBlack);
        b2b.setCreatedAt(new Date());
        b2b.setUpdatedAt(new Date());
        em.persist(b2b);

        b2b = new BookToBookstore();
        b2b.setBookId(book3);
        b2b.setBookstoreId(bookstoreBlack);
        b2b.setCreatedAt(new Date());
        b2b.setUpdatedAt(new Date());
        em.persist(b2b);

        et.commit();
    }
}
