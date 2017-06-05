package de.lathspell.test;

import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import de.lathspell.test.config.AppConfiguration;
import de.lathspell.test.jpa.UserJpaRepo;
import de.lathspell.test.model.User;
import de.lathspell.test.model.UserType;

@RunWith(SpringRunner.class)
@ActiveProfiles("h2") // "h2" or "postgres"
@ContextConfiguration(classes = AppConfiguration.class)
@Slf4j
public class UserJpaRepoTest {

    @Autowired
    private UserJpaRepo userJpaRepo;

    @Test
    @Transactional
    public void crud() {
        assertNotNull(userJpaRepo);

        // Does not exist before
        assertEquals(0, userJpaRepo.findAllByUsername("ttaylor").size());

        // Create
        User user = new User();
        user.setUsername("ttaylor");
        user.setFirstName("Tim");
        user.setLastName("Taylor");
        user.setEmail("ttaylor@example.com");
        user.setUserType(UserType.ADMIN);
        user.setCreatedAt(new java.sql.Date(new Date().getTime()));
        
        assertNull(user.getId());
        userJpaRepo.saveAndFlush(user);
        assertNotNull(user.getId());

        // Retrieve
        user = userJpaRepo.findAllByUsername("ttaylor").get(0);
        assertNotNull(user);
        assertNotNull(user.getId());
        assertEquals("Tim", user.getFirstName());
        log.info("User: " + user);

        // Retrieve via Id
        long id = user.getId();
        User user2 = userJpaRepo.findOne(id);
        assertEquals(user, user2);

        // Retrieve collection
        List<User> users = userJpaRepo.findAll();
        assertEquals(1, users.size());

        // Update
        user.setUserType(UserType.OWNER);
        userJpaRepo.save(user);
        assertEquals(new Long(id), user.getId()); // still same Id
        assertEquals(UserType.OWNER, user.getUserType());

        // Delete
        userJpaRepo.delete(user);
        assertEquals(0, userJpaRepo.findAll().size());
    }

}
