package de.lathspell.test.crypto;

import static org.junit.Assert.assertEquals;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

/**
 * Interessante Links:
 * - http://wiki.dovecot.org/Authentication/PasswordSchemes
 * 
 */
public class HashTest {
    private static String plaintext = "geheim";
    private static String sollMd5 = "e8636ea013e682faf61f56ce1cb1ab5c";
    private static String sollSha1 = "906072001efddf3e11e6d2b5782f4777fe038739";
    private static String sollSha512 = "8d847e01d22baa969f71fa362b4de21c9e13c7882bcea13ba5c6a8ae0d71fc8c9700c82e0087a65c8b37bd29f536747f28c9672bec1cae7762d2c9f36b6013f2";
    private static String sollCrypt = "xxvtL0X0QulBg";
    private static String sollCrypt3Md5 = "$1$xxxxxxxx$xb4kDfFDMF.EcKNOIFMuh1";
    private static String sollCrypt3Sha1 = "$5$xxxxxxxx$ijhlhCdRA2QW6WNt2gTM6DpCZIX63YOhKz1k/yeewq/";
    private static String sollCrypt3Sha512 = "$6$xxxxxxxx$wuSdyeOvQXjj/nNoWnjjo.6OxUWrQFRIj019kh1cDpun6l6cpr3ywSrBprYRYZXcm4Kv9lboCEFI3GzBkdNAz/";

    /** Dieser Aufruf liefert ein MD5 Hash der kompatibel zum MySQL md5() ist. */
    @Test
    public void testMd5Mysql() throws Exception {
        String ist = DigestUtils.md5Hex(plaintext);
        assertEquals(sollMd5, ist);
    }

    @Test
    public void testSha1Commons() throws NoSuchAlgorithmException {
        String ist = DigestUtils.shaHex(plaintext);

        assertEquals(sollSha1, ist);
    }

    @Test
    public void testSha1Native() throws NoSuchAlgorithmException {
        byte[] digest = MessageDigest.getInstance("SHA1").digest(plaintext.getBytes());
        String ist = Hex.encodeHexString(digest);

        assertEquals(sollSha1, ist);
    }

    @Test
    public void testSha512Commons() throws Exception {
        String ist512 = DigestUtils.sha512Hex(plaintext);
        assertEquals(sollSha512, ist512);
    }

    /** Dieser Aufruf liefert einen Hash der kompatibel zum crypt(3) DES ist. */
    @Test
    public void testCrypt3Des() throws Exception {
        String ist = Crypt.crypt("xx", "geheim");
        assertEquals(sollCrypt, ist);
    }

    /** Dieser Aufruf liefert ein MD5 Hash der kompatibel zu crypt(3) $1$ in /etc/shadow ist. */
    @Test
    public void testCrypt3Md5() throws Exception {
        String salt = sollCrypt3Md5.substring(3, 11);
        String ist = MD5Crypt.crypt(plaintext, salt);
        assertEquals(sollCrypt3Md5, ist);
    }

    /** Dieser Aufruf liefert ein SHA-512 Hash der kompatibel zu crypt(3) $6$ in /etc/shadow ist. */
    @Test
    public void testCrypt3Sha512() throws Exception {
        String salt = sollCrypt3Sha512.substring(3, 11);
        String ist = Sha512Crypt.Sha512_crypt(plaintext, salt, 0);
        assertEquals(sollCrypt3Sha512, ist);
    }

}