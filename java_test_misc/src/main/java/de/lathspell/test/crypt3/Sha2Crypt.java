package de.lathspell.test.crypt3;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SHA2-based Unix crypt implementation.
 *
 * <p> Based on the C implementation released into the Public Domain by Ulrich
 * Drepper &lt;drepper@redhat.com&gt;
 * http://www.akkadia.org/drepper/SHA-crypt.txt </p>
 *
 * <p> Conversion to Kotlin and from there to Java by Christian Hammers
 * &lt;ch@lathspell.de&gt; and likewise put into the Public Domain. </p>
 *
 */
public class Sha2Crypt {

    /**
     * The prefixes that can be used to identify this crypt() variant.
     */
    public static String SHA256_PREFIX = "$5$";
    public static String SHA512_PREFIX = "$6$";
    /**
     * The number of bytes the final hash value will have.
     */
    private static int SHA256_BLOCKSIZE = 32;
    private static int SHA512_BLOCKSIZE = 64;
    /**
     * The MessageDigest algorithm.
     */
    private static String SHA256_ALGORITHM = "SHA-256";
    private static String SHA512_ALGORITHM = "SHA-512";
    /**
     * Prefix for optional rounds specification.
     */
    private static String ROUNDS_PREFIX = "rounds=";
    /**
     * Default number of rounds if not explicitly specified.
     */
    private static int ROUNDS_DEFAULT = 5000;
    /**
     * Minimum number of rounds.
     */
    private static int ROUNDS_MIN = 1000;
    /**
     * Maximum number of rounds.
     */
    private static int ROUNDS_MAX = 999999999;

    /**
     * Generates a libc crypt() compatible "$5$" hash value with random salt.
     *
     * See {@link Crypt#crypt(java.lang.String, java.lang.String)} for details.
     */
    public static String sha256Crypt(byte[] key_bytes) throws Exception {
        return sha256Crypt(key_bytes, null);
    }

    /**
     * Generates a libc6 crypt() compatible "$5$" hash value.
     *
     * See {@link Crypt#crypt(java.lang.String, java.lang.String)} for details.
     */
    public static String sha256Crypt(byte[] key_bytes, String salt) throws Exception {
        if (salt == null) {
            salt = SHA256_PREFIX + B64.getRandomSalt(8);
        }
        return sha2Crypt(key_bytes, salt, SHA256_PREFIX, SHA256_BLOCKSIZE, SHA256_ALGORITHM);
    }

    /**
     * Generates a libc crypt() compatible "$6$" hash value with random salt.
     *
     * See {@link Crypt#crypt(java.lang.String, java.lang.String)} for details.
     */
    public static String sha512Crypt(byte[] key_bytes) throws Exception {
        return sha512Crypt(key_bytes, null);
    }

    /**
     * Generates a libc6 crypt() compatible "$6$" hash value.
     *
     * See {@link Crypt#crypt(java.lang.String, java.lang.String)} for details.
     */
    public static String sha512Crypt(byte[] key_bytes, String salt) throws Exception {
        if (salt == null) {
            salt = SHA512_PREFIX + B64.getRandomSalt(8);
        }
        return sha2Crypt(key_bytes, salt, SHA512_PREFIX, SHA512_BLOCKSIZE, SHA512_ALGORITHM);
    }

    /**
     * Generates a libc6 crypt() compatible "$5$" or "$6$" SHA2 based hash
     * value.
     *
     * This is a nearly line by line conversion of the original C function. The
     * numbered comments are from the algorithm description, the short C-style
     * ones from the original C code and the ones with "Remark" from me.
     *
     * See {@link Crypt#crypt(java.lang.String, java.lang.String)} for details.
     *
     * @param key_bytes The plaintext that should be hashed.
     * @param salt_string The real salt value without prefix or "rounds=".
     * @param salt_prefix Either $5$ or $6$.
     * @param blocksize A value that differs between $5$ and $6$.
     * @param algorithm The MessageDigest algorithm identifier string.
     * @return The complete hash value including prefix and salt.
     */
    private static String sha2Crypt(byte[] key_bytes, String salt, String salt_prefix, int blocksize, String algorithm) throws Exception {
        int key_len = key_bytes.length;

        // Extracts effective salt and the number of rounds from the given salt.
        int rounds = ROUNDS_DEFAULT;
        boolean rounds_custom = false;
        if (salt == null) {
            throw new IllegalArgumentException("Invalid salt value: null");
        }
        Pattern p = Pattern.compile("^\\$([56])\\$(rounds=(\\d+)\\$)?([\\.\\/a-zA-Z0-9]{1,16}).*");
        Matcher m = p.matcher(salt);
        if (m == null || !m.find()) {
            throw new IllegalArgumentException("Invalid salt value: " + salt);
        }
        if (m.group(3) != null) {
            rounds = Integer.valueOf(m.group(3));
            rounds = Math.max(ROUNDS_MIN, Math.min(ROUNDS_MAX, rounds));
            rounds_custom = true;
        }
        String salt_string = m.group(4);
        byte[] salt_bytes = salt_string.getBytes();
        int salt_len = salt_bytes.length;

        // 1.  start digest A
        // Prepare for the real work.
        MessageDigest ctx = MessageDigest.getInstance(algorithm);

        // 2.  the password string is added to digest A
        /*
         * Add the key string.
         */
        ctx.update(key_bytes);

        // 3.  the salt string is added to digest A.  This is just the salt string
        // itself without the enclosing '$', without the magic salt_prefix $5$ and
        // $6$ respectively and without the rounds=<N> specification.
        //
        // NB: the MD5 algorithm did add the $1$ salt_prefix.  This is not deemed
        // necessary since it is a constant string and does not add security
        // and /possibly/ allows a plain text attack.  Since the rounds=<N>
        // specification should never be added this would also create an
        // inconsistency.
        /*
         * The last part is the salt string. This must be at most 16 characters
         * and it ends at the first `$' character (for compatibility with
         * existing implementations).
         */
        ctx.update(salt_bytes);

        // 4.  start digest B
        /*
         * Compute alternate sha512 sum with input KEY, SALT, and KEY. The final
         * result will be added to the first context.
         */
        MessageDigest alt_ctx = MessageDigest.getInstance(algorithm);

        // 5.  add the password to digest B
        /*
         * Add key.
         */
        alt_ctx.update(key_bytes);

        // 6.  add the salt string to digest B
        /*
         * Add salt.
         */
        alt_ctx.update(salt_bytes);

        // 7.  add the password again to digest B
        /*
         * Add key again.
         */
        alt_ctx.update(key_bytes);

        // 8.  finish digest B
        /*
         * Now get result of this (32 bytes) and add it to the other context.
         */
        byte[] alt_result = alt_ctx.digest();


        // 9.  For each block of 32 or 64 bytes in the password string (excluding
        // the terminating NUL in the C representation), add digest B to digest A
        /*
         * Add for any character in the key one byte of the alternate sum.
         */
        /*
         * (Remark: the C code comment seems wrong for key length > 32!)
         */
        int cnt = key_bytes.length;
        while (cnt > blocksize) {
            ctx.update(alt_result, 0, blocksize);
            cnt -= blocksize;
        }

        // 10. For the remaining N bytes of the password string add the first
        // N bytes of digest B to digest A
        ctx.update(alt_result, 0, cnt);

        // 11. For each bit of the binary representation of the length of the
        // password string up to and including the highest 1-digit, starting
        // from to lowest bit position (numeric value 1):
        //
        // a) for a 1-digit add digest B to digest A
        //
        // b) for a 0-digit add the password string
        //
        // NB: this step differs significantly from the MD5 algorithm.  It
        // adds more randomness.
        /*
         * Take the binary representation of the length of the key and for every
         * 1 add the alternate sum, for every 0 the key.
         */
        cnt = key_bytes.length;
        while (cnt > 0) {
            if ((cnt & 1) != 0) {
                ctx.update(alt_result, 0, blocksize);
            } else {
                ctx.update(key_bytes);
            }
            cnt >>= 1;
        }

        // 12. finish digest A
        /*
         * Create intermediate result.
         */
        alt_result = ctx.digest();

        // 13. start digest DP
        /*
         * Start computation of P byte sequence.
         */
        alt_ctx = MessageDigest.getInstance(algorithm);

        // 14. for every byte in the password (excluding the terminating NUL byte
        // in the C representation of the string)
        //
        //   add the password to digest DP
        /*
         * For every character in the password add the entire password.
         */
        for (int i = 1; i <= key_len; i++) {
            alt_ctx.update(key_bytes);
        }

        // 15. finish digest DP
        /*
         * Finish the digest.
         */
        byte[] temp_result = alt_ctx.digest();

        // 16. produce byte sequence P of the same length as the password where
        //
        //     a) for each block of 32 or 64 bytes of length of the password string
        //        the entire digest DP is used
        //
        //     b) for the remaining N (up to  31 or 63) bytes use the first N
        //        bytes of digest DP
        /*
         * Create byte sequence P.
         */
        byte[] p_bytes = new byte[key_len];
        int cp = 0;
        while (cp < key_len - blocksize) {
            System.arraycopy(temp_result, 0, p_bytes, cp, blocksize);
            cp += blocksize;
        }
        System.arraycopy(temp_result, 0, p_bytes, cp, key_len - cp);

        // 17. start digest DS
        /*
         * Start computation of S byte sequence.
         */
        alt_ctx = MessageDigest.getInstance(algorithm);

        // 18. repeast the following 16+A[0] times, where A[0] represents the first
        //     byte in digest A interpreted as an 8-bit unsigned value
        //
        //       add the salt to digest DS
        /*
         * For every character in the password add the entire password.
         */
        for (int i = 1; i <= (16 + (alt_result[0] & 0xff)); i++) {
            alt_ctx.update(salt_bytes);
        }

        // 19. finish digest DS
        /*
         * Finish the digest.
         */
        temp_result = alt_ctx.digest();

        // 20. produce byte sequence S of the same length as the salt string where
        //
        //     a) for each block of 32 or 64 bytes of length of the salt string
        //        the entire digest DS is used
        //
        //     b) for the remaining N (up to  31 or 63) bytes use the first N
        //        bytes of digest DS
        /*
         * Create byte sequence S.
         */
        // Remark: The salt is limited to 16 chars, how does this make sense?
        byte[] s_bytes = new byte[salt_len];
        cp = 0;
        while (cp < salt_len - blocksize) {
            System.arraycopy(temp_result, 0, s_bytes, cp, blocksize);
            cp += blocksize;
        }
        System.arraycopy(temp_result, 0, s_bytes, cp, salt_len - cp);

        // 21. repeat a loop according to the number specified in the rounds=<N>
        //     specification in the salt (or the default value if none is
        //     present).  Each round is numbered, starting with 0 and up to N-1.
        //
        //     The loop uses a digest as input.  In the first round it is the
        //     digest produced in step 12.  In the latter steps it is the digest
        //     produced in step 21.h.  The following text uses the notation
        //     "digest A/C" to desribe this behavior.
        /*
         * Repeatedly run the collected hash value through sha512 to burn CPU
         * cycles.
         */
        for (int i = 0; i <= (rounds - 1); i++) {
            // a) start digest C
            /*
             * New context.
             */
            ctx = MessageDigest.getInstance(algorithm);

            // b) for odd round numbers add the byte sequense P to digest C
            // c) for even round numbers add digest A/C
            /*
             * Add key or last result.
             */
            if ((i & 1) != 0) {
                ctx.update(p_bytes, 0, key_len);
            } else {
                ctx.update(alt_result, 0, blocksize);
            }

            // d) for all round numbers not divisible by 3 add the byte sequence S
            /*
             * Add salt for numbers not divisible by 3.
             */
            if (i % 3 != 0) {
                ctx.update(s_bytes, 0, salt_len);
            }

            // e) for all round numbers not divisible by 7 add the byte sequence P
            /*
             * Add key for numbers not divisible by 7.
             */
            if (i % 7 != 0) {
                ctx.update(p_bytes, 0, key_len);
            }

            // f) for odd round numbers add digest A/C
            // g) for even round numbers add the byte sequence P
            /*
             * Add key or last result.
             */
            if ((i & 1) != 0) {
                ctx.update(alt_result, 0, blocksize);
            } else {
                ctx.update(p_bytes, 0, key_len);
            }

            // h) finish digest C.
            /*
             * Create intermediate result.
             */
            alt_result = ctx.digest();
        }

        // 22. Produce the output string.  This is an ASCII string of the maximum
        //     size specified above, consisting of multiple pieces:
        //
        //     a) the salt salt_prefix, $5$ or $6$ respectively
        //
        //     b) the rounds=<N> specification, if one was present in the input
        //        salt string.  A trailing '$' is added in this case to separate
        //        the rounds specification from the following text.
        //
        //     c) the salt string truncated to 16 characters
        //
        //     d) a '$' character
        /*
         * Now we can construct the result string. It consists of three parts.
         */
        StringBuffer buffer = new StringBuffer(salt_prefix + (rounds_custom ? ROUNDS_PREFIX + rounds + "$" : "") + salt_string + "$");

        // e) the base-64 encoded final C digest.  The encoding used is as
        //    follows:
        // [...]
        //
        //    Each group of three bytes from the digest produces four
        //    characters as output:
        //
        //    1. character: the six low bits of the first byte
        //    2. character: the two high bits of the first byte and the
        //       four low bytes from the second byte
        //    3. character: the four high bytes from the second byte and
        //       the two low bits from the third byte
        //    4. character: the six high bits from the third byte
        //
        // The groups of three bytes are as follows (in this sequence).
        // These are the indices into the byte array containing the
        // digest, starting with index 0.  For the last group there are
        // not enough bytes left in the digest and the value zero is used
        // in its place.  This group also produces only three or two
        // characters as output for SHA-512 and SHA-512 respectively.

        // This was just a safeguard in the C implementation:
        // int buflen = salt_prefix.length() - 1 + ROUNDS_PREFIX.length() + 9 + 1 + salt_string.length() + 1 + 86 + 1;

        if (blocksize == 32) {
            B64.b64from24bit(alt_result[0], alt_result[10], alt_result[20], 4, buffer);
            B64.b64from24bit(alt_result[21], alt_result[1], alt_result[11], 4, buffer);
            B64.b64from24bit(alt_result[12], alt_result[22], alt_result[2], 4, buffer);
            B64.b64from24bit(alt_result[3], alt_result[13], alt_result[23], 4, buffer);
            B64.b64from24bit(alt_result[24], alt_result[4], alt_result[14], 4, buffer);
            B64.b64from24bit(alt_result[15], alt_result[25], alt_result[5], 4, buffer);
            B64.b64from24bit(alt_result[6], alt_result[16], alt_result[26], 4, buffer);
            B64.b64from24bit(alt_result[27], alt_result[7], alt_result[17], 4, buffer);
            B64.b64from24bit(alt_result[18], alt_result[28], alt_result[8], 4, buffer);
            B64.b64from24bit(alt_result[9], alt_result[19], alt_result[29], 4, buffer);
            B64.b64from24bit((byte) 0, alt_result[31], alt_result[30], 3, buffer);
        } else {
            B64.b64from24bit(alt_result[0], alt_result[21], alt_result[42], 4, buffer);
            B64.b64from24bit(alt_result[22], alt_result[43], alt_result[1], 4, buffer);
            B64.b64from24bit(alt_result[44], alt_result[2], alt_result[23], 4, buffer);
            B64.b64from24bit(alt_result[3], alt_result[24], alt_result[45], 4, buffer);
            B64.b64from24bit(alt_result[25], alt_result[46], alt_result[4], 4, buffer);
            B64.b64from24bit(alt_result[47], alt_result[5], alt_result[26], 4, buffer);
            B64.b64from24bit(alt_result[6], alt_result[27], alt_result[48], 4, buffer);
            B64.b64from24bit(alt_result[28], alt_result[49], alt_result[7], 4, buffer);
            B64.b64from24bit(alt_result[50], alt_result[8], alt_result[29], 4, buffer);
            B64.b64from24bit(alt_result[9], alt_result[30], alt_result[51], 4, buffer);
            B64.b64from24bit(alt_result[31], alt_result[52], alt_result[10], 4, buffer);
            B64.b64from24bit(alt_result[53], alt_result[11], alt_result[32], 4, buffer);
            B64.b64from24bit(alt_result[12], alt_result[33], alt_result[54], 4, buffer);
            B64.b64from24bit(alt_result[34], alt_result[55], alt_result[13], 4, buffer);
            B64.b64from24bit(alt_result[56], alt_result[14], alt_result[35], 4, buffer);
            B64.b64from24bit(alt_result[15], alt_result[36], alt_result[57], 4, buffer);
            B64.b64from24bit(alt_result[37], alt_result[58], alt_result[16], 4, buffer);
            B64.b64from24bit(alt_result[59], alt_result[17], alt_result[38], 4, buffer);
            B64.b64from24bit(alt_result[18], alt_result[39], alt_result[60], 4, buffer);
            B64.b64from24bit(alt_result[40], alt_result[61], alt_result[19], 4, buffer);
            B64.b64from24bit(alt_result[62], alt_result[20], alt_result[41], 4, buffer);
            B64.b64from24bit((byte) 0, (byte) 0, alt_result[63], 2, buffer);
        }

        /*
         * Clear the buffer for the intermediate result so that people attaching
         * to processes or reading core dumps cannot get any information.
         */
        // Is there a better way to do this with the JVM?
        Arrays.fill(temp_result, (byte) 0);
        Arrays.fill(p_bytes, (byte) 0);
        Arrays.fill(s_bytes, (byte) 0);
        ctx.reset();
        alt_ctx.reset();
        Arrays.fill(key_bytes, (byte) 0);
        Arrays.fill(salt_bytes, (byte) 0);

        return buffer.toString();
    }
}
