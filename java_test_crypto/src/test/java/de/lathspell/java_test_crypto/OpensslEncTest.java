package de.lathspell.java_test_crypto;

import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.junit.Assert.assertEquals;

import de.lathspell.java_test_crypto.OpensslEnc.Derived;

import static de.lathspell.java_test_crypto.OpensslEnc.CIPHER.AES_256_CBC;
import static de.lathspell.java_test_crypto.OpensslEnc.MD.MD5;
import static de.lathspell.java_test_crypto.OpensslEnc.MD.SHA1;

/**
 * Tests for encryption and decryption based on actual "openssl enc" output.
 *
 * Example with AES-256-CBC and MD5:
 *
 * <pre>
 * $ echo -n plaintext | openssl enc -a -A -aes-256-cbc -p -k secret -md md5; echo
 * U2FsdGVkX18P2O2mPbQYsalt=0FD8EDA63DB41849
 * key=F02573ADC3BEF0EE559FC6BA3CA77844B1DA9D5CA86D32CF78457044DE28A132
 * iv =FA336C9B3399DA0401141A686A83B338
 * Se9+c4couUI10B8sLehnT9Y=
 *
 * $ echo -n "U2FsdGVkX18P2O2mPbQYSe9+c4couUI10B8sLehnT9Y=" | openssl enc -d -a -A -aes-256-cbc -p -k secret -md md5
 * salt=0FD8EDA63DB41849
 * key=F02573ADC3BEF0EE559FC6BA3CA77844B1DA9D5CA86D32CF78457044DE28A132
 * iv =FA336C9B3399DA0401141A686A83B338
 * plaintext
 * </pre>
 *
 * Example with AES-256-CBC and SHA1:
 *
 * <pre>
 * $ echo -n plaintext | openssl enc -a -A -aes-256-cbc -p -k secret -md sha1
 * salt=27CF3EA6D991F0CB
 * key=C2ED9BB2E49A0CFB4ABB18AB4E0D7CA0216C6DFC2AC5E0436482A9C93C7CFF98
 * iv =D31A12CFAD91E7F1001D5DACD5117C66
 * U2FsdGVkX18nzz6m2ZHwy8QybR0LKej64scjS9wrWZQ=
 *
 * $ echo -n U2FsdGVkX18nzz6m2ZHwy8QybR0LKej64scjS9wrWZQ= | openssl enc -d -a -A -aes-256-cbc -p -k secret -md sha1
 * salt=27CF3EA6D991F0CB
 * key=C2ED9BB2E49A0CFB4ABB18AB4E0D7CA0216C6DFC2AC5E0436482A9C93C7CFF98
 * iv =D31A12CFAD91E7F1001D5DACD5117C66
 * plaintext
 * </pre>
 */
@RunWith(Parameterized.class)
public class OpensslEncTest {

    @Parameters(name = "{0}/{1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(
                // openssl enc -a -A -aes-256-cbc -p -k secret -md md5
                new Object[]{AES_256_CBC, MD5,
                    "0FD8EDA63DB41849",
                    "F02573ADC3BEF0EE559FC6BA3CA77844B1DA9D5CA86D32CF78457044DE28A132",
                    "FA336C9B3399DA0401141A686A83B338",
                    "U2FsdGVkX18P2O2mPbQYSe9+c4couUI10B8sLehnT9Y="
                },
                // openssl enc -a -A -aes-256-cbc -p -k secret -md sha1
                new Object[]{AES_256_CBC, SHA1,
                    "27CF3EA6D991F0CB",
                    "C2ED9BB2E49A0CFB4ABB18AB4E0D7CA0216C6DFC2AC5E0436482A9C93C7CFF98",
                    "D31A12CFAD91E7F1001D5DACD5117C66",
                    "U2FsdGVkX18nzz6m2ZHwy8QybR0LKej64scjS9wrWZQ="
                }
        );
    }

    private final String plaintext = "plaintext";

    private final String password = "secret";

    private final OpensslEnc.CIPHER cipher;

    private final OpensslEnc.MD md;

    private final String saltHex;

    private final String keyHex;

    private final String ivHex;

    private final String base64Ciphertext;

    public OpensslEncTest(OpensslEnc.CIPHER cipher, OpensslEnc.MD md, String saltHex, String keyHex, String ivHex, String base64Ciphertext) {
        this.cipher = cipher;
        this.md = md;
        this.saltHex = saltHex;
        this.keyHex = keyHex;
        this.ivHex = ivHex;
        this.base64Ciphertext = base64Ciphertext;
    }

    @Test
    public void testDerivation() throws Exception {
        final byte[] salt = Hex.decodeHex(saltHex.toCharArray());

        Derived derived = OpensslEnc.opensslPBKDF(md.name(), password.getBytes("UTF-8"), salt, cipher.keySize, cipher.ivSize);

        assertEquals(ivHex, Hex.encodeHexString(derived.iv).toUpperCase());
        assertEquals(keyHex, Hex.encodeHexString(derived.key).toUpperCase());
    }

    @Test
    public void testEncryption() throws Exception {
        String output = OpensslEnc.encrypt(cipher, md, password, plaintext.getBytes("UTF-8"));
        assertEquals(44, output.length());

        // Well, can't exactly test the output, as it is different each time
        // due to the random salt. We can at least try to decrypt with our
        // own function, though.
        byte[] decrypted = OpensslEnc.decrypt(cipher, md, password, output);
        assertEquals(plaintext, new String(decrypted));
    }

    @Test
    public void testDecryption() throws Exception {
        byte[] output = OpensslEnc.decrypt(cipher, md, password, base64Ciphertext);
        assertEquals(plaintext, new String(output));
    }

}
