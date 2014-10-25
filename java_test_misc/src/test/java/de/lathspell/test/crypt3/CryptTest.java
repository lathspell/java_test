package de.lathspell.test.crypt3;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class CryptTest {

    @Test
    public void testDefaultCryptVariant() throws Exception {
        assertTrue(Crypt.crypt("secret").startsWith("$6$"));
    }

    /**
     * An empty string as salt is invalid as it could not be verified by
     * a later call to crypt(3).
     */
    @Test
    public void testMd5CryptEmptySalt() {
        Exception catched = null;
        try {
            Crypt.crypt("secret", "");
        } catch (Exception e) {
            catched = e;
        }
        assertNotNull(catched);
    }
}
