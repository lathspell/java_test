package test2;

import lombok.extern.slf4j.Slf4j;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import test2.config.Test2Config;
import test2.security.MyUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Test2Config.class)
@Slf4j
public class UserDetailsTest {

    @Autowired
    private AuthenticationManager authManager;

    @Test
    public void testAdmin() {
        String user = "tim";
        String pass = "t1mmy";
        Authentication request = new UsernamePasswordAuthenticationToken(user, pass);

        // Authenticating
        Authentication result = authManager.authenticate(request);
        SecurityContextHolder.getContext().setAuthentication(result); // store for later

        // Checking...
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // Checking for granted roles
        assertEquals("[RO, STATS]", auth.getAuthorities().toString());

        // Checking if UserDetails are present
        assertNull(auth.getDetails()); // no, that's used for other things!
        MyUser userDetails = (MyUser) auth.getPrincipal();
        assertEquals("tim@example.com", userDetails.getEmail());
        assertNull(userDetails.getPassword()); // don't want this here for security reasons
    }

}
