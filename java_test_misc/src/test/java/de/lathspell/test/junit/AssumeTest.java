package de.lathspell.test.junit;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

public class AssumeTest {

    @Test
    public void test1() {
        String buggy = "has been fixed"; // will be set once bug has been removed
        assumeTrue("Bug is still present", buggy == null);
        // Test will be skipped as long as the above is true!
        assertTrue(false); // never executed
    }

}
