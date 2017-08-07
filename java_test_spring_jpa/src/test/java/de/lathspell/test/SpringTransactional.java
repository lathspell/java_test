package de.lathspell.test;

import lombok.extern.slf4j.Slf4j;
import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import de.lathspell.test.config.AppConfiguration;
import de.lathspell.test.model.User;
import de.lathspell.test.service.UserService;

/**
 * The recommended way of using Transactions in Spring is the declarative one with @Transactional.
 *
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("h2") // "h2" or "postgres"
@ContextConfiguration(classes = AppConfiguration.class)
@Slf4j
public class SpringTransactional {

    @Autowired
    private UserService svc;

    @Test
    public void testIfReallyProxied() {
        assertThat(svc.getRepo().getClass().getName(), matchesPattern(".*Proxy.*"));
    }

    @Test
    @Rollback(false)
    public void testSpringTransactional() {
        User u = svc.createUserWithDeclarativeTx("Tim");
        log.info("User u: " + u);
        assertNotNull(u);
        assertEquals("Tim", u.getUsername());
        assertNotNull("Tim", u.getId());

        User u2 = svc.createUserWithProgrammaticTx("Tim");
        svc.getRepo().flush();
        log.info("User u2: " + u2);
        assertNotNull(u2);
        assertEquals("Tim", u2.getUsername());
        assertNotEquals(u.getId(), u2.getId());
        
    }
}
