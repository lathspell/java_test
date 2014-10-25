package de.lathspell.java_test_ee7_stack1;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static junit.framework.Assert.assertTrue;

public class SimpleTest {

    public static final Logger log = LoggerFactory.getLogger(SimpleTest.class);

    @Test
    public void test1() {
        log.info("Logging during junit test.");

        assertTrue(true);
    }

}
