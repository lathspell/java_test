package de.lathspell.test.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class CommonsLoggingTest {
    private static Log log = LogFactory.getLog("test");
    
    @Test
    public void test() {       
        log.info("test123");
    }
    
}
