package de.lathspell.java_test_jooq;

import java.io.StringWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import javax.xml.bind.JAXB;

import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultConnectionProvider;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.lathspell.java_test_jooq.generated.enums.BookStatus;
import de.lathspell.java_test_jooq.generated.tables.daos.AuthorDao;
import de.lathspell.java_test_jooq.generated.tables.daos.BookDao;
import de.lathspell.java_test_jooq.generated.tables.daos.BugDao;
import de.lathspell.java_test_jooq.generated.tables.daos.LanguageDao;
import de.lathspell.java_test_jooq.generated.tables.pojos.Author;
import de.lathspell.java_test_jooq.generated.tables.pojos.Book;
import de.lathspell.java_test_jooq.generated.tables.pojos.Bug;

import static org.junit.Assert.*;
import static de.lathspell.java_test_jooq.generated.tables.Author.AUTHOR;
import static de.lathspell.java_test_jooq.generated.tables.Book.BOOK;
import static de.lathspell.java_test_jooq.generated.tables.Bug.BUG;
import static de.lathspell.java_test_jooq.generated.tables.Language.LANGUAGE;

public class DAOTest {

    private static DSLContext jooq;
    private static Connection conn;
    private static AuthorDao authorDao;
    private static BookDao bookDao;
    private static LanguageDao languageDao;
    private static BugDao bugDao;

    @BeforeClass
    public static void start() throws Exception {
        Class.forName(com.mysql.jdbc.Driver.class.getName());
        conn = DriverManager.getConnection("jdbc:mysql://localhost/java_test_jooq", "root", "");
        assertNotNull(conn);
        Settings settings = new Settings().withRenderSchema(false);

        // Active Record
        jooq = DSL.using(conn, SQLDialect.MYSQL, settings);

        // DataMapper
        Configuration configuration = new DefaultConfiguration().set(new DefaultConnectionProvider(conn)).set(SQLDialect.MYSQL).set(settings);
        authorDao = new AuthorDao(configuration);
        bookDao = new BookDao(configuration);
        bugDao = new BugDao(configuration);

        languageDao = new LanguageDao(configuration);
    }

    @AfterClass
    public static void stop() throws Exception {
        if (conn != null) {
            conn.close();
        }
    }

    @Before
    public void setUp() throws Exception {
        jooq.deleteQuery(BOOK).execute();
        jooq.deleteQuery(AUTHOR).execute();
        jooq.deleteQuery(LANGUAGE).execute();
        jooq.deleteQuery(BUG).execute();

        assertEquals(new Integer(0), jooq.selectCount().from(AUTHOR).fetchOne().getValue(0, Integer.class));
        assertEquals(new Integer(0), jooq.selectCount().from(BOOK).fetchOne().getValue(0, Integer.class));
        assertEquals(new Integer(0), jooq.selectCount().from(LANGUAGE).fetchOne().getValue(0, Integer.class));
        assertEquals(new Integer(0), jooq.selectCount().from(BUG).fetchOne().getValue(0, Integer.class));
    }

    @Test
    public void testCrudWithDsl() throws SQLException {
        // INSERT INTO author
        //   (id, first_name, last_name)
        // VALUES
        //   (100, 'Hermann', 'Hesse'),
        //   (101, 'Alfred', 'Döblin');
        Author author1 = new Author();
        author1.setId(100);
        author1.setFirstName("Hermann");
        author1.setLastName("Hesse");
        Author author2 = new Author();
        author2.setId(101);
        author2.setFirstName("Alfred");
        author2.setLastName("Döblin");
        authorDao.insert(author1, author2);
        assertEquals(2, authorDao.count());
        assertNotNull(author1.getId());
        assertNotNull(author2.getId());
        assertNotEquals(author1.getId(), author2.getId());
        assertNull(author1.getDistinguished()); // BUG - Ignoriert Defaultwert der Datenbank!

        // UPDATE author SET first_name=..., last_name=... WHERE id = 100;
        // BEWARE: Always sets *all* fields as it does not have a Proxy object in the
        //         background like other DataMappers like Hibernate/EclipseLink!
        //         See http://www.jooq.org/doc/2.5/manual/sql-execution/fetching/pojos/
        Author author3 = authorDao.findById(100);
        author3.setFirstName("Enyd");
        author3.setLastName("Blyton");
        authorDao.update(author3);
        assertEquals(2, authorDao.count());

        // SELECT
        Author author = jooq.select().from(AUTHOR).where(AUTHOR.FIRST_NAME.eq("Enyd")).fetchOne().into(Author.class);
        assertNotNull(author);
        assertEquals(new Integer(100), author.getId());
        assertEquals("Enyd", author.getFirstName());
        assertEquals("Blyton", author.getLastName());

        // SELECT (partial)
        Author pauthor = jooq.select(AUTHOR.LAST_NAME).from(AUTHOR).where(AUTHOR.FIRST_NAME.eq("Enyd")).fetchOne().into(Author.class);
        assertNotNull(pauthor);
        assertEquals(null, pauthor.getId());
        assertEquals(null, pauthor.getFirstName());
        assertEquals("Blyton", pauthor.getLastName());

        // DELETE FROM author WHERE ID = 100;
        authorDao.deleteById(103);
        assertEquals(2, authorDao.count());
    }

    @Test
    public void testEnum() throws SQLException {
        List<Book> books = bookDao.fetchByStatus(BookStatus.sold_out);
        assertEquals(0, books.size());
    }

    @Test
    public void testOutputFormats() throws SQLException {
        jooq.insertInto(AUTHOR, AUTHOR.ID, AUTHOR.FIRST_NAME, AUTHOR.LAST_NAME, AUTHOR.DATE_OF_BIRTH).
                values(100, "Hermann", "Hesse", Date.valueOf("1920-02-03")).
                execute();
        List<Author> authors = authorDao.findAll();
        assertEquals(1, authors.size());

        //
        // XML
        //
        // BEWARE: JAXB is too stupid to convert java.sql.Date!
        //
        StringWriter sw = new StringWriter();
        JAXB.marshal(authors.get(0), sw);
        String xmlActual = sw.toString();
        String xmlWanted = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                + "<author>\n"
                + "    <dateOfBirth/>\n"
                + "    <firstName>Hermann</firstName>\n"
                + "    <id>100</id>\n"
                + "    <lastName>Hesse</lastName>\n"
                + "</author>\n";
        assertEquals(xmlWanted, xmlActual);
    }

    @Test
    public void DefaultValueBugWithDataMapper() throws Exception {
        Bug b = new Bug();
        b.setFoo(3);
        bugDao.insert(b); // Fails because it tries to set "bar" to NULL
    }
}
