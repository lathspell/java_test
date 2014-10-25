package de.lathspell.test.crypt3;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class B64Test {

    @Test
    public void testB64from24bit() {
        StringBuffer buffer = new StringBuffer("");
        B64.b64from24bit((byte) 8, (byte) 16, (byte) 64, 2, buffer);
        B64.b64from24bit((byte) 7, (byte) 77, (byte) 120, 4, buffer);
        assertEquals("./spo/", buffer.toString());
    }
}
