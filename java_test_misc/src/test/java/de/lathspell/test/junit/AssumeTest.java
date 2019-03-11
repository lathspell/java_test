package de.lathspell.test.junit;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeTrue;

public class AssumeTest {

    @Test
    public void testOnlyIfConditionIsTrue() {
        String received = "foo";
        assumeTrue("Remote system happens to be online", received != null);
        // Test will be skipped if the above is false!
        assertEquals("foo", received);
    }

    @Test
    public void testOnlyIfConditionIsTrue2() {
        String received = null;
        assumeTrue("Remote system happens to be online", received != null);
        // Test will be skipped if the above is false!
        assertEquals("foo", received);
    }
}
