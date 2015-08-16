package de.lathspell.java_test_crypto;

import java.security.SecureRandom;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;

/**
 * Password Based Encryption using the Java Cryptography Architecture.
 *
 * AES in CBC mode needs a secret key and an Initialization Vector to
 * encrypt/decrypt data.
 *
 * As the secret key must be of a specific size, usually 128 or 256 bits,
 * a Password Based Key Derivation Function (PBKDF) must be used to create
 * those 256 bits from the usually 4-8 bytes short user given password.
 *
 * A random salt is used to ensure that the resulting key is different every
 * time even for the same password. Therefore this salt, too, is necessary
 * to decipher the encrypted data again.
 *
 * Salt and IV are supposed to be stored unencrypted alongside with the
 * encrypted data. Only the user given password has to be kept secret.
 *
 * Glossary:
 * - PBE: password based encryption
 * - PBKDF: password based key derivation function
 * - HMAC: hash-based message authentication code
 * - AES: advanced encryption standard
 * - CBC: cipher block chaining
 * - PKCS: Public Key Cryptography Standards (by RSA Security Inc.)
 *
 */
public class JcsPbEncryption {

    /**
     * Necessary information for decryption.
     */
    public static class Triplet {

        public byte[] salt;

        public byte[] iv;

        public byte[] ciphertext;

        public Triplet(byte[] salt, byte[] iv, byte[] ciphertext) {
            this.salt = salt;
            this.iv = iv;
            this.ciphertext = ciphertext;
        }

    }

    /** Key derivation function like PBKDF2WithHmacSHA1. */
    private final String kdf;

    /** Number of rounds for the key derivation function. */
    private final int kdfRounds;

    /** Cipher algorithm like "AES". */
    private final String algoName;

    /** Cipher algorithm mode specification like "AES/CBC/PKCS5Padding". */
    private final String algoSpec;

    /** Number of secret key bits like 128 or 256. */
    private final int keyBits;

    public JcsPbEncryption(String kdf, int kdfRounds, String algo, String algoSpec, int keyBits) {
        this.kdf = kdf;
        this.kdfRounds = kdfRounds;
        this.algoName = algo;
        this.algoSpec = algoSpec;
        this.keyBits = keyBits;
    }

    /**
     * Encrypts data.
     *
     * @param plaintext The data to encrypt.
     * @param password A user given password.
     * @return Salt, IV and ciphertext bytes.
     * @throws Exception
     */
    public Triplet encrypt(byte[] plaintext, String password) throws Exception {
        // Generate random salt für Passwort Derivation Function
        byte[] salt = new SecureRandom().generateSeed(8);

        // Create secret key from random salt and password
        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, kdfRounds, keyBits);
        byte[] keyBytes = SecretKeyFactory.getInstance(kdf).generateSecret(keySpec).getEncoded();

        // Initialize Cipher and generate IV and ciphertext
        Cipher cipher = Cipher.getInstance(algoSpec);
        cipher.init(ENCRYPT_MODE, new SecretKeySpec(keyBytes, algoName));
        byte[] iv = cipher.getParameters().getParameterSpec(IvParameterSpec.class).getIV();
        byte[] ciphertext = cipher.doFinal(plaintext);

        // Return IV and ciphertext
        return new Triplet(salt, iv, ciphertext);
    }

    /**
     * Decrypts data.
     *
     * @param password The user given password.
     * @param salt The salt that was used when encrypting.
     * @param iv The IV that was used when encrypting.
     * @param ciphertext The encrypted data.
     * @return
     * @throws Exception
     */
    byte[] decrypt(String password, byte[] salt, byte[] iv, byte[] ciphertext) throws Exception {
        // Create secret key from given salt and password
        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, kdfRounds, keyBits);
        SecretKey secretKey = SecretKeyFactory.getInstance(kdf).generateSecret(keySpec);
        byte[] keyBytes = secretKey.getEncoded();

        // Decrypt ciphertext with key and IV
        Cipher cipher = Cipher.getInstance(algoSpec);
        cipher.init(DECRYPT_MODE, new SecretKeySpec(keyBytes, algoName), new IvParameterSpec(iv));
        byte[] plaintext = cipher.doFinal(ciphertext);

        // Return plaintext bytes
        return plaintext;
    }

}
