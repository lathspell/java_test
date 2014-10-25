package de.lathspell.java_test_ee6_jpa.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import static org.junit.Assert.*;

import static de.lathspell.java_test_ee6_jpa.model.EnumExample.MyEnum.*;

public class EnumExampleMysqlTest extends EnumExampleTest {

    @Override
    protected Pu getPersistenceUnitName() {
        return Pu.mysqlJavaTestJpaPU;
    }

    @Test
    public void testMysqlEnum() throws SQLException {
        EnumExampleMysql mee = new EnumExampleMysql();
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
        ResultSet rs = conn.prepareStatement("SELECT as_enum FROM mysql_enum_example").executeQuery();
        rs.next();
        assertEquals("BLUE", rs.getString(1));

        // Check actual database type - JDBC MetaData is not enough
        rs = conn.getMetaData().getColumns(null, null, "mysql_enum_example", "as_enum");
        rs.next();
        assertEquals("ENUM", rs.getString("TYPE_NAME"));

        // Check actual database type - via information_schema
        rs = conn.prepareStatement("SELECT column_type, column_default, is_nullable FROM information_schema.columns WHERE table_schema = database() and table_name = 'mysql_enum_example' and column_name = 'as_enum'").executeQuery();
        assertTrue(rs.next());
        assertEquals("enum('RED','GREEN','BLUE')", rs.getString("column_type"));
        assertEquals("RED", rs.getString("column_default"));
        assertEquals("NO", rs.getString("is_nullable"));

        // Check actual database type - via SHOW CREATE TABLE
        rs = conn.prepareStatement("SHOW CREATE TABLE mysql_enum_example").executeQuery();
        assertTrue(rs.next());
        assertTrue(rs.getString(2).contains("`as_enum` enum('RED','GREEN','BLUE') NOT NULL DEFAULT 'RED',"));

        et.commit();
    }
}
