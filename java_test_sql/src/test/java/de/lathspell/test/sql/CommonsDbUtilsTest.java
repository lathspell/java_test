package de.lathspell.test.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.apache.commons.dbutils.*;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.KeyedHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class CommonsDbUtilsTest {

    QueryRunner run;

    @Before
    public void setUp() throws Exception {
        MysqlDataSource ds = new MysqlDataSource();
        ds.setURL(Credentials.DBURL);
        ds.setUser(Credentials.DBUSER);
        ds.setPassword(Credentials.DBPASS);
        Connection conn = ds.getConnection();
        assertTrue("conn ist keine Verbindugn", conn instanceof Connection);

        conn.createStatement().execute("DROP TABLE IF EXISTS junit");
        conn.createStatement().execute("CREATE TABLE junit (i int, s varchar(255)) ENGINE=InnoDB");

        run = new QueryRunner(ds); // DBUtils Wrapper
        int inserted = run.update("INSERT INTO junit SET i = ?, s = ?", 10, "aa");
        assertEquals(1, inserted);
    }

    @Test
    public void testMapListHandler() throws SQLException {
        List<Map<String, Object>> rows = run.query("SELECT i, s FROM junit WHERE i = 10", new MapListHandler());
        assertTrue(rows.get(0).get("i") instanceof Integer);
        assertTrue(rows.get(0).get("s") instanceof String);
        assertEquals(10, rows.get(0).get("i"));
        assertEquals("aa", rows.get(0).get("s"));
    }

    /**
     * Testet den ScalarHandler zur schnellen Abfrage eines einzelnen Werts.
     */
    @Test
    public void testScalarHandler() throws SQLException {
        int i = (Integer) run.query("SELECT i FROM junit", new ScalarHandler());
        assertEquals(10, i);
    }

    /**
     * Dieser Handler kopiert alle Zeilen anhand einer Spalte in eine Map.
     */
    @Test
    public void testKeyedHandler() throws SQLException {
        Map found = (Map) run.query("SELECT i, s FROM junit", new KeyedHandler("i"));
        Map row10 = (Map) found.get(10);
        assertEquals("aa", row10.get("s"));
    }

    @Test
    public void testBeanListHandler() throws SQLException {
        ResultSetHandler<List<JunitRow>> h = new BeanListHandler<JunitRow>(JunitRow.class);
        List<JunitRow> rows = run.query("SELECT * FROM junit", h);
        assertEquals(1, rows.size());
        assertEquals(new Integer(10), rows.get(0).i);
        assertEquals("aa", rows.get(0).s);
    }
}
