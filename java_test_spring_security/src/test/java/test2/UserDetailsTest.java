package test2;

import lombok.extern.slf4j.Slf4j;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import test2.config.Test2Config;
import test2.security.MyUser;
import test2.service.MySecuredService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Test2Config.class)
@Slf4j
public class UserDetailsTest {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private MySecuredService mySecuredService;

    @Test
    public void testAdmin() {
        log.info("*** testAdmin ***");
        String user = "admin";
        String pass = "secret";
        Authentication request = new UsernamePasswordAuthenticationToken(user, pass);

        // Authenticating
        Authentication result = authManager.authenticate(request);
        SecurityContextHolder.getContext().setAuthentication(result); // store for later

        // Checking...
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // Checking for granted roles
     //   assertEquals("[ROLE_ADMIN, VIEW_STATS]", auth.getAuthorities().toString());

        // Checking if UserDetails are present
        assertNull(auth.getDetails()); // no, that's used for other things!
        MyUser userDetails = (MyUser) auth.getPrincipal();
        assertEquals("admin@example.com", userDetails.getEmail());
        assertNull(userDetails.getPassword()); // don't want this here for security reasons

        // Check method calls
        mySecuredService.onlyForAdmins();
        mySecuredService.onlyForAdmins2();
        mySecuredService.onlyForAdmins3();
        mySecuredService.viewStats();
        mySecuredService.freeForAll();
    }

    @Test
    public void testTim() {
        log.info("*** testTim ***");
        String user = "tim";
        String pass = "t1mmy";
        Authentication request = new UsernamePasswordAuthenticationToken(user, pass);

        // Authenticating
        Authentication result = authManager.authenticate(request);
        SecurityContextHolder.getContext().setAuthentication(result); // store for later

        // Checking...
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // Checking for granted roles
        assertEquals("[ROLE_USER, VIEW_STATS]", auth.getAuthorities().toString());

        // Checking...
        mySecuredService.viewStats();
        mySecuredService.freeForAll();
    }

    @Test(expected = AccessDeniedException.class)
    public void testTimBad() {
        log.info("*** testTimBad ***");
        String user = "tim";
        String pass = "t1mmy";
        Authentication request = new UsernamePasswordAuthenticationToken(user, pass);

        // Authenticating
        Authentication result = authManager.authenticate(request);
        SecurityContextHolder.getContext().setAuthentication(result); // store for later

        // Checking...
        mySecuredService.onlyForAdmins();
    }
}
