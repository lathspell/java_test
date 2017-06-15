package de.lathspell.test.spring_jpa.transactions;

import lombok.extern.slf4j.Slf4j;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
@ActiveProfiles("postgres") // "h2" or "postgres"
@ContextConfiguration(classes = AppConfiguration.class)
@Slf4j
public class SpringTransactional {

    @Autowired
    private UserService svc;

    @Test
    public void testSpringTransactional() {

        User u = svc.createAndFindUser("Tim");

        assertEquals("Tim", u.getUsername());
        assertNotNull("Tim", u.getId());
    }
}
