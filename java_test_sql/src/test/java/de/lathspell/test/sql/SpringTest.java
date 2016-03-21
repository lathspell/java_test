package de.lathspell.test.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

class MyRow {
    public Integer i;
    public String s;

    public MyRow(Integer i, String s) {
        this.i=i; this.s=s;
    }

    @Override
    public String toString() {
        return "[i=" + i + ", s=" + s + "]";
    }
}


public class SpringTest {
    MysqlDataSource mysql_ds;
    JdbcTemplate template;

    boolean canary;

    /** Diese Funktion ist kein Constructor sondern wird vor jedem einzelnen Testfunktion aufgerufen! */
    @Before
    public void setUp() throws Exception {
        mysql_ds = new MysqlDataSource();
        mysql_ds.setURL(Credentials.DBURL);
        mysql_ds.setUser(Credentials.DBUSER);
        mysql_ds.setPassword(Credentials.DBPASS);

        template = new JdbcTemplate(mysql_ds);
        Assert.assertTrue("template ist keine JdbcTemplate", template != null);

        template.execute("DROP TABLE IF EXISTS junit");
        template.execute("CREATE TABLE junit (i int, s varchar(255)) ENGINE=InnoDB");
        System.out.println("# setUp done");
    }

    /** Diese Funktion wird nach jedem einzelnen Test aufgerufen! */
    @After
    public void tearDown() throws Exception {
        template.execute("DROP TABLE junit");
    }

    @Test
    public void select_with_jdbcTemplate() throws Exception {
        // Einfaches Update
        template.update("INSERT INTO junit SET i=?, s=?", new Object[] { 1, "eins" });

        // Batch Update
        template.batchUpdate("INSERT INTO junit SET i=?, s=?", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement arg0, int arg1) throws SQLException {
                arg0.setInt(1, 10 + (int) (Math.random() * 1000));
                arg0.setString(2, String.valueOf(arg1));
            }

            @Override
            public int getBatchSize() {
                return 4;
            }
        });

        // Einfaches Select
        @SuppressWarnings("unchecked")
        List<Map<String, Integer>> result = template
                .queryForList("SELECT i FROM junit WHERE i = ?", new Object[] { 1 });
        assertTrue(result.get(0).get("i") == 1);

        // Einfaches Select mit vielen Rückgabezeilen
        SqlRowSet rs = template.queryForRowSet("SELECT i FROM junit WHERE i > ? ORDER BY i", new Object[] { 0 });
        assertTrue(rs.first());
        assertTrue(rs.getInt("i") == 1);

        // SELECT mit RowMapper
        canary = false;
        template.query("SELECT i, s FROM junit", new RowMapper() {
            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                assertTrue(rs.getString("s").equals("eins"));
                canary = true;
                do {
                    System.out.println("Row " + rowNum + " hat i=" + rs.getInt("i") + ", s=" + rs.getString("s"));
                } while (rs.next());
                return null;
            }
        });
        assertTrue(canary);
    }

}