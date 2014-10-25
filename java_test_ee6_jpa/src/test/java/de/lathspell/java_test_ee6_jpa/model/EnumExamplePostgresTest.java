package de.lathspell.java_test_ee6_jpa.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import static org.junit.Assert.*;

import static de.lathspell.java_test_ee6_jpa.model.EnumExample.MyEnum.*;

public class EnumExamplePostgresTest extends EnumExampleTest {

    @Override
    protected Pu getPersistenceUnitName() {
        return Pu.postgresJavaTestJpaPU;
    }

    @Test
    public void testPostgresEnum() throws SQLException {
        EnumExamplePostgres mee = new EnumExamplePostgres();
        mee.asEnum = BLUE;

        et.begin();
        em.persist(mee);
        em.flush();
        em.refresh(mee);
        et.commit();

        // em.unwrap() only works within a transaction!
        et.begin();

        // Check actual database values
        Connection conn = em.unwrap(Connection.class);
        ResultSet rs = conn.prepareStatement("SELECT as_enum FROM postgres_enum_example").executeQuery();
        rs.next();
        assertEquals("BLUE", rs.getString(1));

        // Check actual database type - JDBC MetaData is not enough
        rs = conn.getMetaData().getColumns(null, null, "postgres_enum_example", "as_enum");
        rs.next();
        assertEquals("my_enum", rs.getString("TYPE_NAME"));

        // Check actual database type - via information_schema
        rs = conn.prepareStatement("SELECT data_type, column_default, is_nullable FROM information_schema.columns WHERE table_catalog = current_database() and table_schema = current_schema() and table_name = 'postgres_enum_example' and column_name = 'as_enum'").executeQuery();
        assertTrue(rs.next());
        assertEquals("USER-DEFINED", rs.getString("data_type")); // WTF?
        assertEquals("'RED'::my_enum", rs.getString("column_default")); // Aha, "my_enum"! :)
        assertEquals("NO", rs.getString("is_nullable"));

        // Check actual database type - via SHOW CREATE TABLE
        rs = conn.prepareStatement("SELECT pg_typeof(as_enum) FROM postgres_enum_example").executeQuery();
        assertTrue(rs.next());
        assertEquals("my_enum", rs.getString(1));

        et.commit();
    }
}
