package de.lathspell.test.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

class MySimpleRow {

    public int i;
    public String s;

    @Override
    public String toString() {
        return "i=" + i + " and s=" + s + "!";
    }
}

public class SimpleSpringTest {

    MysqlDataSource dsMysql;
    SimpleJdbcTemplate template;

    /**
     * Diese Funktion ist kein Constructor sondern wird vor jedem einzelnen
     * Testfunktion aufgerufen!
     */
    @Before
    public void setUp() throws Exception {
        dsMysql = new MysqlDataSource();
        dsMysql.setURL(Credentials.DBURL);
        dsMysql.setUser(Credentials.DBUSER);
        dsMysql.setPassword(Credentials.DBPASS);

        template = new SimpleJdbcTemplate(dsMysql);
        assertTrue("template ist keine JdbcTemplate", template != null);

        template.update("DROP TABLE IF EXISTS junit");
        template.update("CREATE TABLE junit (i int, s varchar(255)) ENGINE=InnoDB");
    }

    /**
     * Diese Funktion wird nach jedem einzelnen Test aufgerufen!
     */
    @After
    public void tearDown() throws Exception {
        template.update("DROP TABLE junit");
    }

    @Test
    public void selectWithRowMapper() throws Exception {
        // Einfaches Update
        template.update("INSERT INTO junit SET i=?, s=?", 1, "eins");

        // Typensicheres Query
        List<MySimpleRow> result = template.query("SELECT i, s FROM junit WHERE i = ?",
                new ParameterizedRowMapper<MySimpleRow>() {

                    @Override
                    public MySimpleRow mapRow(ResultSet rs, int n) throws SQLException {
                        MySimpleRow m = new MySimpleRow();
                        m.i = rs.getInt("i");
                        m.s = rs.getString("s");
                        return m;
                    }
                }, 1);
        assertTrue(result.get(0).i == 1);
    }

    /**
     * Mappt das Ergebnis automatisch in ein Objekt der angegebenen Klasse.
     *
     * Vorsicht: Beschwert sich nicht wenn es keinen passenden Setter gibt!
     */
    @Test
    public void selectWithBeanPropertyMapper() {
        template.update("INSERT INTO junit SET i=?, s=?", 1, "eins");
        List<JunitRow> rows = template.query("SELECT i, s, i*3 as hasNoSetter FROM junit WHERE i = 1",
                ParameterizedBeanPropertyRowMapper.newInstance(JunitRow.class));
        assertTrue(rows.get(0).i == 1);
        assertTrue(rows.get(0).s.equals("eins"));
        assertNull(rows.get(0).hasNoSetter); // buuuh!
    }

    @Test
    public void select_with_old_jdbcTemplate() throws Exception {
        // Einfaches Update
        template.update("INSERT INTO junit SET i=?, s=?", 1, "eins");

        // Batch Update
        template.getJdbcOperations().batchUpdate("INSERT INTO junit SET i=?, s=?", new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement pstmt, int value) throws SQLException {
                pstmt.setInt(1, 10 + (int) (Math.random() * 1000));
                pstmt.setString(2, String.valueOf(value));
            }

            @Override
            public int getBatchSize() {
                return 4;
            }
        });
    }
}