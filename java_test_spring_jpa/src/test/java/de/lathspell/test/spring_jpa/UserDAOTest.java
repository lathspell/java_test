package de.lathspell.test.spring_jpa;

import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import de.lathspell.test.config.AppConfiguration;
import de.lathspell.test.jpa.plain_jpa.UserDAO;
import de.lathspell.test.model.User;
import de.lathspell.test.model.UserType;

@RunWith(SpringRunner.class)
@ActiveProfiles("postgres") // "h2" or "postgres"
@ContextConfiguration(classes = AppConfiguration.class)
@Slf4j
public class UserDAOTest {

    @Autowired
    private UserDAO userDAO;

    @Test
    @Transactional
    public void crud() {
        assertNotNull(userDAO);

        // Does not exist before
        assertEquals(0, userDAO.findAllByUsername("ttaylor").size());

        // Create
        User user = new User();
        user.setUsername("ttaylor");
        user.setFirstName("Tim");
        user.setLastName("Taylor");
        user.setEmail("ttaylor@example.com");
        user.setUserType(UserType.ADMIN);
        user.setCreatedAt(new java.sql.Date(new Date().getTime()));
        userDAO.save(user);

        // Retrieve
        user = userDAO.findAllByUsername("ttaylor").get(0);
        assertNotNull(user);
        assertNotNull(user.getId());
        assertEquals("Tim", user.getFirstName());
        log.info("User: " + user);

        // Retrieve via Id
        long id = user.getId();
        User user2 = userDAO.findOne(id);
        assertEquals(user, user2);

        // Retrieve collection
        List<User> users = userDAO.findAll();
        assertEquals(1, users.size());

        // Update
        user.setUserType(UserType.OWNER);
        userDAO.merge(user);
        assertEquals(new Long(id), user.getId()); // still same Id
        assertEquals(UserType.OWNER, user.getUserType());

        // Delete
        userDAO.remove(user);
        assertEquals(0, userDAO.findAll().size());
    }

}
