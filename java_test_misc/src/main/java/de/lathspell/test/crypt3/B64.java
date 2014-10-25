package de.lathspell.test.crypt3;

import java.util.Random;

/** Base64 like method to convert binary bytes into ASCII chars. */
public class B64 {

    /**
     * Table with characters for base64 transformation.
     */
    public static String B64T = "./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    /**
     * Base64 like conversion of bytes to ASCII chars.
     *
     * @param B2 A byte from the result.
     * @param B1 A byte from the result.
     * @param B0 A byte from the result.
     * @param N The number of expected output chars.
     * @param buffer Where the output chars is appended to.
     */
    public static void b64from24bit(byte B2, byte B1, byte B0, int N, StringBuffer buffer) {
        // The bit masking is necessary because the JVM byte type is signed!
        int w = ((B2 << 16) & 0x00ffffff) | ((B1 << 8) & 0x00ffff) | (B0 & 0xff);
        // It's effectively a "for" loop but kept to resemble the original C code.
        int n = N;
        while (n-- > 0) {
            buffer.append(B64T.charAt(w & 0x3f));
            w >>= 6;
        }
    }

    /** Generates a string of random chars from the B64T set.
     *
     * @param num Number of chars to generate.
     */
    public static String getRandomSalt(int num) {
        String salt_string = "";
        for (int i = 1; i <= num; i++) {
            salt_string += B64T.charAt(new Random().nextInt(B64T.length()));
        }
        return salt_string;
    }
}
