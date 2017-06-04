package de.lathspell.test;

import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import de.lathspell.test.config.AppConfiguration;
import de.lathspell.test.jpa.repo1.UserRepo;
import de.lathspell.test.jpa.repo1.UserRepoJpa;
import de.lathspell.test.model.User;
import de.lathspell.test.model.UserType;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfiguration.class)
@Slf4j
public class JpaTest {

    @Autowired
    @Qualifier("userRepoJpa")
    private UserRepo userRepo;

    @Autowired
    private UserRepoJpa userRepoJpa;

    @Test
    @Transactional
    public void crud() {
        assertNotNull(userRepo);

        // Does not exist before
        assertEquals(0, userRepo.findAllByUsername("ttaylor").size());

        // Create
        User user = new User();
        user.setUsername("ttaylor");
        user.setFirstName("Tim");
        user.setLastName("Taylor");
        user.setEmail("ttaylor@example.com");
        user.setUserType(UserType.ADMIN);
        user.setCreatedAt(new java.sql.Date(new Date().getTime()));
        userRepo.save(user);

        // Retrieve
        user = userRepo.findAllByUsername("ttaylor").get(0);
        assertNotNull(user);
        assertNotNull(user.getId());
        assertEquals("Tim", user.getFirstName());
        log.info("User: " + user);

        // Retrieve via Id
        long id = user.getId();
        User user2 = userRepo.find(id);
        assertEquals(user, user2);

        // Retrieve collection
        List<User> users = userRepo.findAll();
        assertEquals(1, users.size());

        // Update
        user.setUserType(UserType.OWNER);
        userRepo.merge(user);
        assertEquals(new Long(id), user.getId()); // still same Id
        assertEquals(UserType.OWNER, user.getUserType());

        // Delete
        userRepo.remove(user);
        assertEquals(0, userRepo.findAll().size());
    }

    @Test
    @Transactional
    public void testJpaRepo() {
        assertNotNull(userRepo);
        assertNotNull(userRepoJpa);

        // Does not exist before
        assertEquals(0, userRepoJpa.findAllByUsername("ttaylor").size());

        // Create
        User user = new User();
        user.setUsername("ttaylor");
        user.setFirstName("Tim");
        user.setLastName("Taylor");
        user.setEmail("ttaylor@example.com");
        user.setUserType(UserType.ADMIN);
        user.setCreatedAt(new java.sql.Date(new Date().getTime()));
        userRepo.save(user);

        // Retrieve
        assertEquals(user, userRepoJpa.find(user.getId()));
    }
}
