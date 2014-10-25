package de.lathspell.java_test_ee7_jpa.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import static de.lathspell.java_test_ee7_jpa.model.EnumExample.MyEnum.BLUE;
import static de.lathspell.java_test_ee7_jpa.model.EnumExample.MyEnum.GREEN;
import static de.lathspell.java_test_ee7_jpa.model.EnumExample.MyEnum.RED;

public class EnumExampleTest extends Template {

    @Test
    public void testEnumExample() throws SQLException {
        EnumExample ee = new EnumExample();
        assertEquals(RED, ee.asInt);
        assertEquals(RED, ee.asString);

        ee.asInt = BLUE;
        ee.asString = GREEN;

        et.begin();
        em.persist(ee);
        em.flush();
        em.refresh(ee);
        et.commit();

        // Check model
        assertEquals(BLUE, ee.asInt);
        assertEquals(GREEN, ee.asString);

        // Check column
        et.begin();
        Connection conn = em.unwrap(Connection.class);
        ResultSet rs = conn.prepareStatement("SELECT as_int, as_string FROM enum_example").executeQuery();
        rs.next();
        assertEquals("2", rs.getString(1));
        assertEquals(BLUE.ordinal(), rs.getInt(1));
        assertEquals("GREEN", rs.getString(2));
        assertEquals(GREEN.name(), rs.getString(2));
        et.rollback();
    }
}
