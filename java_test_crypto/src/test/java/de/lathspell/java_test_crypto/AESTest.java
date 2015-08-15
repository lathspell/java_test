package de.lathspell.java_test_crypto;

import java.io.ByteArrayOutputStream;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import static org.apache.commons.codec.binary.Hex.decodeHex;
import static org.apache.commons.codec.binary.Hex.encodeHexString;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Glossary:
 * - PBE: password based encryption
 * - PBKDF: password based key derivation function
 * - HMAC: hash-based message authentication code
 * - AES: advanced encryption standard
 * - CBC: cipher block chaining
 * - PKCS: Public Key Cryptography Standards (by RSA Security Inc.)
 *
 */
public class AESTest {

    private final static String OPENSSL_MAGIC = "Salted__";

    @Test
    public void testAES256CBC() throws Exception {
        String password = "secret";
        String plaintext = "plaintext";

        // Generate random salt für Passwort Derivation Function
        byte[] salt = new SecureRandom().generateSeed(8);
        System.out.println("salt: " + encodeHexString(salt));

        // Derive the key from given password and salt
        final int iterationCount = 65536;
        final int keyLength = 256;
        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, iterationCount, keyLength);
        byte[] keyBytes = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(keySpec).getEncoded();
        SecretKey secret = new SecretKeySpec(keyBytes, "AES");
        System.out.println("key: " + encodeHexString(secret.getEncoded()));

        // Initialize Cipher and generate IV and ciphertext
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(ENCRYPT_MODE, secret);
        byte[] iv = cipher.getParameters().getParameterSpec(IvParameterSpec.class).getIV();
        System.out.println("iv: " + encodeHexString(iv));
        byte[] ciphertext = cipher.doFinal(plaintext.getBytes("UTF-8"));
        System.out.println("cipher: " + encodeHexString(ciphertext));

        // Combine salt and ciphertext in OpenSSL format
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(OPENSSL_MAGIC.getBytes()); // 8 bytes
        baos.write(salt); // 8 bytes
        baos.write(ciphertext); // 16 bytes
        byte[] output = baos.toByteArray();

        // Base64 encode output
        String base64Output = Base64.encodeBase64String(output);

        // Output salt and ciphertext in OpenSSL format
        System.out.println("Output: " + base64Output);

        fail("FIXME: incompatible with OpenSSL!");

        /*
         salt is correct, iv and key are not

         $ echo -n 'U2FsdGVkX1/4M3i1r0gyKn6N36+pm47O0CkTHFkBA6A=' | openssl enc -d -a -A -aes-256-cbc -p -K 0B2DA6DA55BBFC81B817085435EF2B176B23CFAED0B3CAC59A9840E05FED23C9 -iv 8E1A115722CE10351DAA464EEFBFDD68 -S F83378B5AF48322A
         salt=F890ED8CFC7F0000
         key=0B2DA6DA55BBFC81B817085435EF2B176B23CFAED0B3CAC59A9840E05FED23C9
         iv =8E1A115722CE10351DAA464EEFBFDD68
         bad decrypt
         139736184510096:error:06065064:digital envelope routines:EVP_DecryptFinal_ex:bad decrypt:evp_enc.c:529:
         */
    }

    @Test
    public void testOpensslDecryptWithIv() throws Exception {
        /*
         echo -n U2FsdGVkX1+6zyHoQuG2vp7mmqmoWeIQoZSG+I9xJaQ= | openssl enc -d -a -A -aes-256-cbc -p -k secret -md sha; echo
         salt=BACF21E842E1B6BE
         key=9E6DF458103D9A14C111CE1C3E2676AEF43E4DC2DAB8BC3ECF039C318E443342
         iv =24F260AD081F8BA39D5B57AB848CC724
         plaintext
         */

        final String input = "U2FsdGVkX1+6zyHoQuG2vp7mmqmoWeIQoZSG+I9xJaQ=";
        final String password = "secret";

        final String expectedSaltHex = "BACF21E842E1B6BE";
        final String expectedCipherHex = "9EE69AA9A859E210A19486F88F7125A4";
        final String expectedKeyHex = "9E6DF458103D9A14C111CE1C3E2676AEF43E4DC2DAB8BC3ECF039C318E443342";
        final String expectedIvHex = "24F260AD081F8BA39D5B57AB848CC724";
        final String expectedPlaintext = "plaintext";

        // Base64 decode input (non-chunked)
        byte[] inputBytes = Base64.decodeBase64(input);
        assertEquals(32, inputBytes.length);

        // Separate salt and ciphertext in OpenSSL format
        byte[] magic = Arrays.copyOfRange(inputBytes, 0, 8);
        byte[] salt = Arrays.copyOfRange(inputBytes, 8, 16);
        byte[] ciphertext = Arrays.copyOfRange(inputBytes, 16, 32);
        assertArrayEquals(OPENSSL_MAGIC.getBytes(), magic);
        assertEquals(expectedSaltHex, encodeHexString(salt).toUpperCase());
        assertEquals(expectedCipherHex, encodeHexString(ciphertext).toUpperCase());

        /*

         TODO: Apparently OpenSSL's "-md sha1" is not identicall with Java's
         PBKDF2WithHmacSHA1!

         http://openssl.6102.n7.nabble.com/AES-encryption-openssl-salt-and-Doing-it-in-Java-salt-td26441.html
         */
        /*
         // Derive the key from given password and salt
         final int iterationCount = 65536;
         final int keyLength = 256;

         KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, iterationCount, keyLength);
         final String algo = "PBKDF2WithHmacSHA1";
         byte[] keyBytes = SecretKeyFactory.getInstance(algo).generateSecret(keySpec).getEncoded();
         System.out.println("keyBytes expected: " + expectedKeyHex);
         System.out.println("keyBytes: " + encodeHexString(keyBytes));
         SecretKey secret = new SecretKeySpec(keyBytes, "AES");
         System.out.println("secret: " + secret);
         System.out.println("secret: " + secret.getAlgorithm() + " - " + secret.getFormat() + " - " + encodeHexString(secret.getEncoded()));
         */
        final byte[] ivBytes = Hex.decodeHex(expectedIvHex.toCharArray());

        // Initialize Cipher and decode
        byte[] keyBytes = Hex.decodeHex(expectedKeyHex.toCharArray());

        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(DECRYPT_MODE, keySpec, new IvParameterSpec(ivBytes));
        byte[] plaintext = cipher.doFinal(ciphertext);
        String plaintextString = new String(plaintext);
        assertEquals(expectedPlaintext, plaintextString);
    }

    @Test
    public void testFromHex() throws Exception {
        assertArrayEquals(new byte[]{0x1, 0x42, 0x4}, decodeHex("014204".toCharArray()));
        assertEquals("014280", encodeHexString(new byte[]{(byte) 0x01, (byte) 0x42, (byte) 0x80}));
    }

}
