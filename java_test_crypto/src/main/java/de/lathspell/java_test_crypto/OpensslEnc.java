package de.lathspell.java_test_crypto;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;

import org.apache.commons.codec.binary.Base64;

import static java.util.Arrays.copyOfRange;

public class OpensslEnc {

    public static enum CIPHER {

        AES_256_CBC(256, 128, "AES", "AES/CBC/PKCS5Padding");

        int keySize;

        int ivSize;

        String secretKeyAlgo;

        String cipherAlgo;

        private CIPHER(int keySize, int ivSize, String secretKeyAlgo, String cipherAlgo) {
            this.keySize = keySize;
            this.ivSize = ivSize;
            this.secretKeyAlgo = secretKeyAlgo;
            this.cipherAlgo = cipherAlgo;
        }

    }

    public static enum MD {

        SHA1,
        MD5

    }

    static class Derived {

        byte[] key;

        byte[] iv;

        public Derived(byte[] key, byte[] iv) {
            this.key = key;
            this.iv = iv;
        }

    }

    private static final String OPENSSL_SALTED = "Salted__";

    public static String encrypt(CIPHER cipherSpec, MD mdSpec, String password, byte[] plaintext) throws Exception {
        final byte[] salt = new SecureRandom().generateSeed(8);

        Derived derived = opensslPBKDF(mdSpec.name(), password.getBytes("UTF-8"), salt, cipherSpec.keySize, cipherSpec.ivSize);

        // Initialize Cipher and generate IV and ciphertext
        SecretKey secretKey = new SecretKeySpec(derived.key, cipherSpec.secretKeyAlgo);
        Cipher cipher = Cipher.getInstance(cipherSpec.cipherAlgo);
        cipher.init(ENCRYPT_MODE, secretKey, new IvParameterSpec(derived.iv));
        final byte[] ciphertext = cipher.doFinal(plaintext);

        // Combine salt and ciphertext in OpenSSL format
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(OPENSSL_SALTED.getBytes()); // 8 bytes
        baos.write(salt); // 8 bytes
        baos.write(ciphertext); // 16 bytes
        final byte[] output = baos.toByteArray();

        // Base64 encode output
        String base64Output = Base64.encodeBase64String(output);

        // Output salt and ciphertext in OpenSSL format
        return base64Output;
    }

    public static byte[] decrypt(CIPHER cipherSpec, MD mdSpec, String password, String base64Ciphertext) throws Exception {
        // Base64 decode input (non-chunked)
        byte[] inputBytes = Base64.decodeBase64(base64Ciphertext);
        if (inputBytes.length < 32) {
            throw new IllegalArgumentException("Input too short!");
        }

        // Separate salt and ciphertext in OpenSSL format
        byte[] magic = Arrays.copyOfRange(inputBytes, 0, 8);
        byte[] salt = Arrays.copyOfRange(inputBytes, 8, 16);
        byte[] ciphertext = Arrays.copyOfRange(inputBytes, 16, 32);
        if (!Arrays.equals(OPENSSL_SALTED.getBytes(), magic)) {
            throw new IllegalArgumentException("Invalid magic!");
        }

        // Derive key and IV from password and salt
        Derived derived = opensslPBKDF(mdSpec.name(), password.getBytes("UTF-8"), salt, cipherSpec.keySize, cipherSpec.ivSize);

        // Decrypt ciphertext with key and IV
        SecretKeySpec keySpec = new SecretKeySpec(derived.key, cipherSpec.secretKeyAlgo);
        Cipher cipher = Cipher.getInstance(cipherSpec.cipherAlgo);
        cipher.init(DECRYPT_MODE, keySpec, new IvParameterSpec(derived.iv));
        byte[] plaintext = cipher.doFinal(ciphertext);

        return plaintext;
    }

    /**
     * OpenSSL compatible Password Based Key Derivation Function.
     *
     * The "openssl enc" command generates the 256 bit long encryption key
     * as well as the 128 bit long Initialization Vector by creating md5 hashs
     * of the user given password until the result is long enough. This is
     * necessary as the user given password is sometimes only e.g. 6 characters
     * long which would only give 6 * 8 bits = 48 bits as encryption key.
     *
     * The algorithm used with "-md sha1" is similar but not identical to the
     * "PBKDF2WithHmacSHA1" that the SecretKeyFactory of the Java Cryptography
     * Architecture uses. Same goes for "-md md5" and others.
     *
     * The code (and comments) are based on
     * <a href="http://juliusdavies.ca/commons-ssl/pbe.html">not-yet-commons-ssl</a>
     * project (Apache Licence 2.0), version 0.3.17, by Julius Davies which
     * also includes many high level functions that imitate other OpenSSL "enc"
     * commands. Many thanks!
     *
     * @author <a href="mailto:juliusdavies@cucbc.com">juliusdavies@gmail.com</a>
     * @since 18-Oct-2007
     *
     * @author <a href="cb@lathspell.de">Christian Brunotte</a>
     */
    static Derived opensslPBKDF(String javaMdAlgo, byte[] pwdAsBytes, byte[] salt, int keySize, int ivSize) throws Exception {
        MessageDigest md = MessageDigest.getInstance(javaMdAlgo);
        md.reset();
        byte[] keyAndIv = new byte[(keySize / 8) + (ivSize / 8)];
        byte[] result;
        int currentPos = 0;
        while (currentPos < keyAndIv.length) {
            md.update(pwdAsBytes);
            // First 8 bytes of salt ONLY!  That wasn't obvious to me
            // when using AES encrypted private keys in "Traditional
            // SSLeay Format".
            //
            // Example:
            // DEK-Info: AES-128-CBC,8DA91D5A71988E3D4431D9C2C009F249
            //
            // Only the first 8 bytes are salt, but the whole thing is
            // re-used again later as the IV.  MUCH gnashing of teeth!
            md.update(salt, 0, 8);
            result = md.digest();
            int stillNeed = keyAndIv.length - currentPos;
            // Digest gave us more than we need.  Let's truncate it.
            if (result.length > stillNeed) {
                byte[] b = new byte[stillNeed];
                System.arraycopy(result, 0, b, 0, b.length);
                result = b;
            }
            System.arraycopy(result, 0, keyAndIv, currentPos, result.length);
            currentPos += result.length;
            if (currentPos < keyAndIv.length) {
                // Next round starts with a hash of the hash.
                md.reset();
                md.update(result);
            }
        }

        return new Derived(
                copyOfRange(keyAndIv, 0, keySize / 8),
                copyOfRange(keyAndIv, keySize / 8, keySize / 8 + ivSize / 8));
    }

}
