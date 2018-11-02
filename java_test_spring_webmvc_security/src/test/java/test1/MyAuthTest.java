package test1;

import java.util.Arrays;
import java.util.Collection;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import test1.security.MyAuthenticationManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static test1.security.MyAuthenticationManager.AUTH_RW;
import static test1.security.MyAuthenticationManager.AUTH_STATS;

@Slf4j
public class MyAuthTest {

    private final AuthenticationManager authManager = new MyAuthenticationManager();

    @Test
    public void testAdmin() {
        String user = "admin";
        String pass = "secret";

        Authentication request = new UsernamePasswordAuthenticationToken(user, pass);

        Authentication result = authManager.authenticate(request);
        SecurityContextHolder.getContext().setAuthentication(result); // store for later

        // Checking
        Authentication actualAuth = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> expectedRoles = Arrays.asList(AUTH_RW, AUTH_STATS); // sorted
        Collection<? extends GrantedAuthority> actualRoles = actualAuth.getAuthorities();
        assertEquals(expectedRoles.toString(), actualRoles.toString());

        assertEquals(user, actualAuth.getPrincipal()); // "admin"
        assertNull(actualAuth.getDetails()); // not so easy
    }

    @Test(expected = BadCredentialsException.class)
    public void testBad() {
        String user = "bad";
        String pass = "bad";

        Authentication request = new UsernamePasswordAuthenticationToken(user, pass);
        authManager.authenticate(request);
    }

}
