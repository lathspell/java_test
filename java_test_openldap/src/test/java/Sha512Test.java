import org.junit.Test;
import org.springframework.security.crypto.password.LdapSha512PasswordEncoder;

import static org.junit.Assert.*;

public class Sha512Test {

    @Test
    public void test1() {
        LdapSha512PasswordEncoder encoder = new LdapSha512PasswordEncoder();
        String hash1 = encoder.encode("secret");
        System.out.println("secret #1 -> " + hash1);
        assertEquals(105, hash1.length());

        String hash2 = encoder.encode("secret");
        System.out.println("secret #2 -> " + hash2);
        assertEquals(105, hash2.length());

        assertNotEquals(hash1, hash2);

        assertTrue(encoder.matches("secret", hash1));
        assertFalse(encoder.matches("foo", hash1));
    }
}
