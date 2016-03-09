package de.lathspell.test.lombok;

import java.io.IOException;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class SneakyExceptionTest {

    @Test
    public void atSneakyException() {
        AtSneakyException obj = new AtSneakyException();

        Exception caught = null;
        try {
            obj.noCheckedExceptionFromMe();
        } catch (Exception e) {
            caught = e;
        }
        assertNotNull(caught);
        assertTrue(caught instanceof Exception);
        assertTrue(caught instanceof IOException);
        assertFalse(caught instanceof RuntimeException);
    }
}
