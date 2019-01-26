package de.lathspell.java_test_jooq;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.Loader;
import org.jooq.Query;
import org.jooq.Record;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.SelectQuery;
import org.jooq.conf.Settings;
import org.jooq.exception.DataAccessException;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.jooq.impl.DSL.*;

import de.lathspell.java_test_jooq.generated.enums.BookStatus;
import de.lathspell.java_test_jooq.generated.tables.pojos.Author;
import de.lathspell.java_test_jooq.generated.tables.pojos.Bug;
import de.lathspell.java_test_jooq.generated.tables.records.AuthorRecord;
import de.lathspell.java_test_jooq.generated.tables.records.BugRecord;
import static de.lathspell.java_test_jooq.generated.tables.Author.AUTHOR;
import static de.lathspell.java_test_jooq.generated.tables.Book.BOOK;
import static de.lathspell.java_test_jooq.generated.tables.Bug.BUG;
import static de.lathspell.java_test_jooq.generated.tables.Language.LANGUAGE;

public class ActiveRecordTest {

    private static Connection conn;
    private static DSLContext jooq;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost/java_test_jooq?characterEncoding=utf8", "root", "");
        assertNotNull(conn);
        Settings settings = new Settings().withRenderSchema(false);
        jooq = DSL.using(conn, SQLDialect.MYSQL, settings);
        assertNotNull(jooq);
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

        assertEquals(new Integer(0), jooq.selectCount().from(AUTHOR).fetchOne().getValue(0, Integer.class));
        assertEquals(new Integer(0), jooq.selectCount().from(BOOK).fetchOne().getValue(0, Integer.class));
        assertEquals(new Integer(0), jooq.selectCount().from(LANGUAGE).fetchOne().getValue(0, Integer.class));
    }

    @Test
    public void testCrudWithDsl() throws SQLException {
        // INSERT INTO AUTHOR
        //   (ID, FIRST_NAME, LAST_NAME)
        // VALUES
        //   (100, 'Hermann', 'Hesse'),
        //   (101, 'Alfred', 'Döblin');
        int affected = jooq.insertInto(AUTHOR, AUTHOR.ID, AUTHOR.FIRST_NAME, AUTHOR.LAST_NAME, AUTHOR.DISTINGUISHED).
                values(100, "Hermann", "Hesse", 0).
                values(101, "Alfred", "Döblin", 0).
                execute();
        assertEquals(2, affected);

        affected = jooq.insertInto(AUTHOR).
                set(AUTHOR.ID, 102).
                set(AUTHOR.FIRST_NAME, "Oscar").
                set(AUTHOR.LAST_NAME, "Wilde").
                newRecord().
                set(AUTHOR.ID, 103).
                set(AUTHOR.FIRST_NAME, "George").
                set(AUTHOR.LAST_NAME, "Orwell").
                execute();
        assertEquals(2, affected);

        // AutoIncrement + RETURNING
        AuthorRecord record = jooq.insertInto(AUTHOR, AUTHOR.FIRST_NAME, AUTHOR.LAST_NAME).
                values("Charlotte", "Roche").
                returning(AUTHOR.ID, AUTHOR.DISTINGUISHED).
                fetchOne();
        assertNotNull(record);
        assertTrue(record.getValue(AUTHOR.ID) > 103);

        // UPDATE AUTHOR SET FIRST_NAME = 'Enyd', LAST_NAME = 'Blyton' WHERE ID = 100;
        affected = jooq.update(AUTHOR).
                set(AUTHOR.FIRST_NAME, "Enyd").
                set(AUTHOR.LAST_NAME, "Blyton").
                where(AUTHOR.ID.equal(100)).
                execute();
        assertEquals(1, affected);
        assertEquals(new Integer(5), jooq.selectCount().from(AUTHOR).fetchOne().getValue(0, Integer.class));

        // DELETE AUTHOR WHERE ID = 100;
        affected = jooq.delete(AUTHOR).
                where(AUTHOR.ID.equal(103)).
                execute();
        assertEquals(1, affected);
        assertEquals(new Integer(4), jooq.selectCount().from(AUTHOR).fetchOne(0, Integer.class));

        //
        // -- Select all books by authors born after 1920, named "Paulo"
        // -- from a catalogue consisting of authors and books:
        // SELECT *
        // FROM t_author
        // JOIN t_book
        //   ON t_author.id = t_book.author_id
        // WHERE t_author.year_of_birth > 1920
        //   AND t_author.first_name = 'Paulo'
        // ORDER BY t_book.title
        //

        // Execute the query "on a single line"
        Result<Record> result = jooq.select().
                from(AUTHOR).
                join(BOOK).on(AUTHOR.ID.equal(BOOK.AUTHOR_ID)).
                where(AUTHOR.YEAR_OF_BIRTH.greaterThan(1920).and(AUTHOR.FIRST_NAME.equal("Paulo"))).
                orderBy(BOOK.TITLE).
                fetch();

        // Re-use the factory to ctx a SelectQuery. This example will not make use of static imports...
        SelectQuery q = jooq.selectQuery();
        q.addFrom(AUTHOR);
        // This example shows some "mixed" API usage, where the JOIN is added with the standard API, and the
        // Condition is ctxd using the DSL API
        q.addJoin(BOOK, AUTHOR.ID.equal(BOOK.AUTHOR_ID));
        // The AND operator between Conditions is implicit here
        q.addConditions(AUTHOR.YEAR_OF_BIRTH.greaterThan(1920));
        q.addConditions(AUTHOR.FIRST_NAME.equal("Paulo"));
        q.addOrderBy(BOOK.TITLE);
        q.fetch();

        // Fetch via ActiveRecord and store into POJO for export
        AuthorRecord authorRecord = jooq.selectFrom(AUTHOR).where(AUTHOR.FIRST_NAME.eq("Oscar")).fetchOne();
        assertNotNull(authorRecord);
        Author authorPojo = authorRecord.into(Author.class);
        assertNotNull(authorPojo);
        assertEquals("Wilde", authorPojo.getLastName());
    }

    @Test
    public void testToSql() throws SQLException {
        Query q = jooq.delete(AUTHOR).where("id > sqrt(999)");
        String sql = q.getSQL();
        assertEquals("delete from `author` where (id > sqrt(999))", sql);
    }

    @Test
    public void testEnum() throws SQLException {
        jooq.insertInto(LANGUAGE).set(LANGUAGE.ID, 1).set(LANGUAGE.CD, "en").set(LANGUAGE.DESCRIPTION, "English").execute();
        jooq.insertInto(AUTHOR).
                set(AUTHOR.ID, 102).
                set(AUTHOR.FIRST_NAME, "Oscar").
                set(AUTHOR.LAST_NAME, "Wilde").
                execute();
        jooq.insertInto(BOOK).
                set(BOOK.AUTHOR_ID, 102).
                set(BOOK.PUBLISHED_IN, 1).
                set(BOOK.LANGUAGE_ID, 1).
                set(BOOK.TITLE, "1984").
                set(BOOK.STATUS, BookStatus.sold_out).
                execute();

        String title = jooq.select(BOOK.TITLE).from(BOOK).where(BOOK.STATUS.equal(BookStatus.sold_out)).fetchOne(0, String.class);
        assertEquals("1984", title);
    }

    @Test
    public void testComplicated() throws SQLException {
        Record r = jooq.select(AUTHOR.FIRST_NAME, AUTHOR.LAST_NAME, count()).
                from(AUTHOR).
                join(BOOK).on(BOOK.AUTHOR_ID.equal(AUTHOR.ID)).
                where(BOOK.LANGUAGE_ID.equal(1)). // "de"?
                groupBy(AUTHOR.FIRST_NAME, AUTHOR.LAST_NAME).
                having(count().greaterThan(5)).
                orderBy(AUTHOR.LAST_NAME.asc().nullsFirst()).
                limit(1, 2).
                fetchOne();
        assertNull(r);
    }

    @Test
    public void testColumnAlias() throws SQLException {
        // The concat() doesn't compile!
        Record record = jooq.select(
                concat(AUTHOR.FIRST_NAME, val(" "), AUTHOR.LAST_NAME).as("author"),
                count().as("books")).
                from(AUTHOR).
                join(BOOK).on(AUTHOR.ID.equal(BOOK.AUTHOR_ID)).
                groupBy(AUTHOR.FIRST_NAME, AUTHOR.LAST_NAME).
                fetchOne();
        assertNull(record);
    }

    @Test
    public void testPlainSQL() throws SQLException {
        // Abgesehen davon das man immer noch conn.prepareStatement() hat...
        Record record = jooq.select(
                AUTHOR.as("a").LAST_NAME,
                field("count(*) as x"),
                field("count(*) as y")).
                from("author a").
                join("book b").on("a.id = b.author_id").
                where("b.title != ?", "Brida").
                groupBy(AUTHOR.as("a").LAST_NAME).
                orderBy(val("a.LAST_NAME")).
                fetchOne();
        assertNull(record);

        int x = jooq.select(field("round(3.4) as x")).fetchOne(0, Integer.class);
        assertEquals(3, x);

        Record record2 = jooq.fetch("SELECT count(*) as c, 3+0.1415 as pi FROM author au WHERE 1 != ? LIMIT 1", 42).get(0);
        assertNotNull(record2);
        assertEquals(new Long(0), record2.getValue("c"));
        assertEquals(new BigDecimal("3.1415"), record2.getValue("pi"));
    }

    @Test
    public void testOutputFormats() throws SQLException {
        jooq.insertInto(AUTHOR, AUTHOR.ID, AUTHOR.FIRST_NAME, AUTHOR.LAST_NAME).
                values(100, "Hermann", "Hesse").
                execute();
        Result<Record2<Integer, String>> result = jooq.select(AUTHOR.ID, AUTHOR.FIRST_NAME).from(AUTHOR).fetch();

        // XML
        String xml = result.formatXML();
        assertTrue(xml.contains("<field name=\"id\""));
        assertTrue(xml.contains("<value field=\"id\">100</value>"));

        // JSON
        String jsonActual = result.formatJSON();
        String jsonWanted = "{\"fields\":[{\"name\":\"id\",\"type\":\"INTEGER\"},{\"name\":\"first_name\",\"type\":\"VARCHAR\"}],"
                + "\"records\":[[100,\"Hermann\"]]}";
        assertEquals(jsonWanted, jsonActual);

        // ASCII
        String ascii = result.format();
        assertTrue(ascii, ascii.contains("| 100|Hermann   |"));

        // CSV
        String csv = result.formatCSV(',');
        assertEquals(csv, "id,first_name\n100,Hermann\n", csv);
    }

    @Test
    public void testLoadData() throws SQLException, IOException {
        Loader l = jooq.loadInto(AUTHOR)
                .onDuplicateKeyError()
                .onErrorAbort()
                .commitNone()
                .loadCSV("10;Foo;Bar\r\n")
                .fields(AUTHOR.ID, AUTHOR.FIRST_NAME, AUTHOR.LAST_NAME)
                .separator(';')
                .ignoreRows(0)
                .execute();
        assertEquals(0, l.errors().size());
        assertEquals(0, l.ignored());
        assertEquals(1, l.processed());
        assertEquals(1, l.stored());
        assertEquals(new Long(1), jooq.selectCount().from(AUTHOR).fetchOne().getValue(0, Long.class));
        assertEquals("Foo", jooq.select(AUTHOR.FIRST_NAME).from(AUTHOR).fetchOne(0, String.class));
    }

    @Test
    public void testTX() throws SQLException {
        conn.setAutoCommit(false);
        Exception cought = null;
        try {
            jooq.insertInto(BOOK).set(BOOK.TITLE, "MissingId").execute();
            conn.commit();
        } catch (DataAccessException | SQLException e) {
            conn.rollback();
            cought = e;
        }
        assertNotNull(cought);
        assertTrue(cought.getMessage().contains("doesn't have a default value"));
    }

    @Test
    public void DefaultValueBugWithActiveRecord() throws Exception {
        jooq.deleteQuery(BUG).execute();

        BugRecord b = jooq.newRecord(BUG);
        b.setFoo(3);
        b.store();

        // Test object
        assertNull(b.getBar()); // BUG: should be '42'

        // Test database
        Record r = jooq.select().from(BUG).fetchOne();
        assertEquals(new Integer(42), r.getValue(BUG.BAR));
    }
}
