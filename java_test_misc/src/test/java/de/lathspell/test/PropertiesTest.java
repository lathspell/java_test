package de.lathspell.test;

import java.io.InputStream;
import java.util.Properties;

import static org.junit.Assert.*;
import org.junit.Test;

public class PropertiesTest {

    @Test
    public void testGetPropertiesFromResource() throws Exception {
        // this.getClass().getResourceAsStream() does not find the file!
        InputStream is = ClassLoader.getSystemResourceAsStream("test.properties");
        Properties props = new Properties();
        props.load(is);
        assertEquals("bar", props.getProperty("foo"));
    }
}
