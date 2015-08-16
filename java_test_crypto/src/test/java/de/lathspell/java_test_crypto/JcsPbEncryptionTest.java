package de.lathspell.java_test_crypto;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;

import de.lathspell.java_test_crypto.JcsPbEncryption.Triplet;

@RunWith(Parameterized.class)
public class JcsPbEncryptionTest {

    @Parameterized.Parameters(name = "{0} with {3} and {4} bits")
    public static Collection<Object[]> data() {
        return Arrays.asList(
                // Don't use "PBE..." algorithms as they derive the IV from the password - "PBKDF2..." is recommended!
                new Object[]{"PBKDF2WithHmacSHA1", 65536, "AES", "AES/CBC/PKCS5Padding", 256},
                new Object[]{"PBKDF2WithHmacSHA1", 65536, "AES", "AES/CBC/PKCS5Padding", 128},
                new Object[]{"PBKDF2WithHmacSHA1", 65536, "TripleDES", "DESede/CBC/PKCS5Padding", 192} // Just an example, use AES instead!
        );
    }

    final String password = "secret";

    final String plaintext = "plaintext";

    final String kdf; // key derivation function

    final int kdfRounds;

    final String algo;

    final String algoSpec;

    final int keyBits;

    public JcsPbEncryptionTest(String kdf, int kdfRounds, String algo, String algoSpec, int keyBits) {
        this.kdf = kdf;
        this.kdfRounds = kdfRounds;
        this.algo = algo;
        this.algoSpec = algoSpec;
        this.keyBits = keyBits;
    }

    @Test
    public void testAES256CBC() throws Exception {
        // Configure crypto algorithm
        JcsPbEncryption jcsEnc = new JcsPbEncryption(kdf, kdfRounds, algo, algoSpec, keyBits);

        // encrypt
        Triplet result = jcsEnc.encrypt(plaintext.getBytes("UTF-8"), password);

        // decrypt
        byte[] decodedPlaintext = jcsEnc.decrypt(password, result.salt, result.iv, result.ciphertext);

        // compare
        assertEquals(plaintext, new String(decodedPlaintext));
    }

}
