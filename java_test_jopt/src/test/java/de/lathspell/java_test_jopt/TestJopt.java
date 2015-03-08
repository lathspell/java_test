package de.lathspell.java_test_jopt;

import java.util.Arrays;

import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestJopt {

    @Test
    public void test1() {
        OptionParser parser = new OptionParser();
        parser.acceptsAll(Arrays.asList("debug", "d"), "Enable Debugging");
        parser.accepts("count").withRequiredArg().ofType(Integer.class);
        parser.accepts("verbose", "Optional with level 1-3").withOptionalArg();

        OptionSet options = parser.parse("-d", "--count=3", "--verbose");
        assertTrue(options.has("debug"));
        assertEquals(3, options.valueOf("count"));
        assertTrue(options.has("verbose"));
        assertFalse(options.hasArgument("verbose"));

        options = parser.parse("--debug", "--count=3", "--verbose=2");
        assertTrue(options.has("debug"));
        assertEquals(3, options.valueOf("count"));
        assertTrue(options.has("verbose"));
        assertTrue(options.hasArgument("verbose"));
        assertEquals("2", options.valueOf("verbose"));
    }

    @Test(expected = OptionException.class)
    public void testWrongArguments() throws Exception {
        OptionParser parser = new OptionParser();
        parser.accepts("count").withRequiredArg().required();
        parser.parse("");
    }
}
