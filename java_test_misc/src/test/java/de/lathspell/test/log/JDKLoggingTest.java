package de.lathspell.test.log;

import java.util.logging.Logger;

import org.junit.Test;

public class JDKLoggingTest {
    private static Logger log = Logger.getLogger("test");
    
    @Test
    public void test() {
       log.info("test123");
    }
    
}
