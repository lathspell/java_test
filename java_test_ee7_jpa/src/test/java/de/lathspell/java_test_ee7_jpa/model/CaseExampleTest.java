package de.lathspell.java_test_ee7_jpa.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.Test;
import static org.junit.Assert.*;

public class CaseExampleTest extends Template {

    @Test
    public void testCase() throws SQLException {
        et.begin(); // necessary for unwrap()
        Connection conn = em.unwrap(Connection.class);
        assertNotNull(conn);
        ResultSet rs = conn.getMetaData().getColumns(null, null, "CASEEXAMPLE", null);
        assertNotNull(rs);

        boolean found = false;
        while (rs.next()) {
            /*
             * for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
             *     System.out.println("Column " + i + ": " + rs.getMetaData().getColumnLabel(i));
             *     System.out.println("Column " + i + ": " + rs.getString(i));
             * }
             */
            String name = rs.getString("COLUMN_NAME");
            if (name != null && name.equals("FOOBAR")) {
                found = true;
                break;
            }

        }
        assertTrue(found);
        et.rollback();
    }
}
