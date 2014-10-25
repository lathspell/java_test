package de.lathspell.test.arquillian;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Simple test just to see how long Maven takes to run it (2s).
 */
public class SimpleTest {

    @Test
    public void test() throws Exception {
        assertNotNull("foo");
    }
}
