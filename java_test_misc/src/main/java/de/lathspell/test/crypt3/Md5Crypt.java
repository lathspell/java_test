package de.lathspell.test.crypt3;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static de.lathspell.test.crypt3.B64.B64T;
import static de.lathspell.test.crypt3.B64.b64from24bit;

/**
 * MD5-based Unix crypt implementation.
 *
 * <p>
 * Based on the C implementation from Poul-Henning Kamp which was released under
 * the following licence:
 * <pre>
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42): &lt;phk@login.dknet.dk&gt; wrote this file.
 * As long as you retain this notice you can do whatever you want with this
 * stuff. If we meet some day, and you think this stuff is worth it, you can buy
 * me a beer in return. Poul-Henning Kamp
 * ----------------------------------------------------------------------------
 * Source: $FreeBSD: src/lib/libcrypt/crypt-md5.c,v 1.1 1999/01/21 13:50:09 brandon Exp $
 * http://www.freebsd.org/cgi/cvsweb.cgi/src/lib/libcrypt/crypt-md5.c?rev=1.1;content-type=text%2Fplain
 * </pre>
 * <p>
 * Conversion to Kotlin by Christian Hammers &lt;ch@lathspell.de&gt;, no rights
 * reserved.
 * <p>
 * The C style comments are from the original C code, the ones with "//" from
 * me.
 */
public class Md5Crypt {

    /**
     * The Identifier of this crypt() variant.
     */
    public static String MD5_PREFIX = "$1$";
    /**
     * The MessageDigest MD5_ALGORITHM.
     */
    private static String MD5_ALGORITHM = "MD5";
    /**
     * The number of bytes of the final hash.
     */
    private static int BLOCKSIZE = 16;
    /**
     * The number of rounds of the big loop.
     */
    private static int ROUNDS = 1000;

    /** Generates a libc6 crypt() compatible "$1$" hash value.
     *
     * See {@link Crypt#crypt(java.lang.String, java.lang.String)} for details.
     */
    public static String md5Crypt(final byte[] key_bytes) throws Exception {
        return md5Crypt(key_bytes, MD5_PREFIX + B64.getRandomSalt(8));
    }

    /** Generates a libc crypt() compatible "$1$" MD5 based hash value.
     *
     * See {@link Crypt#crypt(java.lang.String, java.lang.String)} for details.
     * 
     * @param key_bytes The plaintext string that should be hashed.
     * @param salt      Salt string including the prefix and optionally garbage
     *                  at the end. Will be generated randomly if null.
     */
    public static String md5Crypt(final byte[] key_bytes, final String salt) throws Exception {
        int key_len = key_bytes.length;

        // Extract the real salt from the given string which can be a complete hash string.
        String salt_string = "";
        if (salt == null) {
            salt_string = B64.getRandomSalt(8);
        } else {
            Pattern p = Pattern.compile("^\\$1\\$([\\.\\/a-zA-Z0-9]{1,8}).*");
            Matcher m = p.matcher(salt);
            if (m == null || !m.find()) {
                throw new IllegalArgumentException("Invalid salt value: " + salt);
            }
            salt_string = m.group(1);
        }
        byte[] salt_bytes = salt_string.getBytes();

        MessageDigest ctx = MessageDigest.getInstance(MD5_ALGORITHM);

        /*
         * The password first, since that is what is most unknown
         */
        ctx.update(key_bytes);

        /*
         * Then our magic string
         */
        ctx.update(MD5_PREFIX.getBytes());

        /*
         * Then the raw salt
         */
        ctx.update(salt_bytes);


        /*
         * Then just as many characters of the MD5(pw,salt,pw)
         */
        MessageDigest ctx1 = MessageDigest.getInstance(MD5_ALGORITHM);
        ctx1.update(key_bytes);
        ctx1.update(salt_bytes);
        ctx1.update(key_bytes);
        byte[] finalb = ctx1.digest();
        int ii = key_len;
        while (ii > 0) {
            ctx.update(finalb, 0, (ii > 16) ? 16 : ii);
            ii -= 16;
        }

        /*
         * Don't leave anything around in vm they could use.
         */
        Arrays.fill(finalb, (byte) 0);

        /*
         * Then something really weird...
         */
        ii = key_len;
        int j = 0;
        while (ii > 0) {
            if ((ii & 1) == 1) {
                ctx.update(finalb[j]);
            } else {
                ctx.update(key_bytes[j]);
            }
            ii >>= 1;
        }

        /*
         * Now make the output string
         */
        StringBuffer passwd = new StringBuffer(MD5_PREFIX + salt_string + "$");
        finalb = ctx.digest();

        /*
         * and now, just to make sure things don't run too fast On a 60 Mhz
         * Pentium this takes 34 msec, so you would need 30 seconds to build a
         * 1000 entry dictionary...
         */
        for (int i = 0; i < ROUNDS; i++) {
            ctx1 = MessageDigest.getInstance(MD5_ALGORITHM);
            if ((i & 1) != 0) {
                ctx1.update(key_bytes);
            } else {
                ctx1.update(finalb, 0, BLOCKSIZE);
            }

            if ((i % 3) != 0) {
                ctx1.update(salt_bytes);
            }

            if ((i % 7) != 0) {
                ctx1.update(key_bytes);
            }

            if ((i & 1) != 0) {
                ctx1.update(finalb, 0, BLOCKSIZE);
            } else {
                ctx1.update(key_bytes);
            }
            finalb = ctx1.digest();
        }

        // The following was nearly identical to the Sha2Crypt code.
        // Again, the buflen is not really needed.
        // int buflen = MD5_PREFIX.length() - 1 + salt_string.length() + 1 + BLOCKSIZE + 1;
        b64from24bit(finalb[0], finalb[6], finalb[12], 4, passwd);
        b64from24bit(finalb[1], finalb[7], finalb[13], 4, passwd);
        b64from24bit(finalb[2], finalb[8], finalb[14], 4, passwd);
        b64from24bit(finalb[3], finalb[9], finalb[15], 4, passwd);
        b64from24bit(finalb[4], finalb[10], finalb[5], 4, passwd);
        b64from24bit((byte) 0, (byte) 0, finalb[11], 2, passwd);

        /*
         * Don't leave anything around in vm they could use.
         */
        // Is there a better way to do this with the JVM?
        ctx.reset();
        ctx1.reset();
        Arrays.fill(key_bytes, (byte) 0);
        Arrays.fill(salt_bytes, (byte) 0);
        Arrays.fill(finalb, (byte) 0);

        return passwd.toString();
    }
}
