package de.lathspell.test.lombok;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class AtLogTest {

    @Test
    public void atLog() {
        AtLog obj = new AtLog();
        assertNotNull(obj); // no idea how to verify output on stdout
    }
}
