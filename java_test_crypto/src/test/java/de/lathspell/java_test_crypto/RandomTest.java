package de.lathspell.java_test_crypto;

import java.security.SecureRandom;

import org.junit.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * As recommended on this site:
 *
 * https://paragonie.com/blog/2016/05/how-generate-secure-random-numbers-in-various-programming-languages#java-csprng
 *
 */
public class RandomTest {

    @Test
    public void testRandomNumber() {
        SecureRandom csprng = new SecureRandom();
        byte[] randomBytes = new byte[32];
        csprng.nextBytes(randomBytes);
        assertNotNull(randomBytes);

        byte[] randomBytes2 = new byte[32];
        csprng.nextBytes(randomBytes2);
        assertNotNull(randomBytes2);

        assertNotEquals(randomBytes, randomBytes2);
    }

}
