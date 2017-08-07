package de.lathspell.test.transactions;

import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import de.lathspell.test.config.AppConfiguration;
import de.lathspell.test.model.User;
import de.lathspell.test.model.UserType;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfiguration.class)
@Slf4j
public class HibernateTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    @Transactional
    public void crud() {
        Session session = sessionFactory.getCurrentSession();

        // Does not exist before
        User user = session.createQuery("FROM User u WHERE username = :username", User.class).setParameter("username", "ttaylor").uniqueResult();
        assertNull(user);

        // Create
        user = new User();
        user.setUsername("ttaylor");
        user.setFirstName("Tim");
        user.setLastName("Taylor");
        user.setEmail("ttaylor@example.com");
        user.setUserType(UserType.ADMIN);
        user.setCreatedAt(new java.sql.Date(new Date().getTime()));
        session.save(user);

        // Retrieve
        user = session.createQuery("FROM User u WHERE username = :username", User.class).setParameter("username", "ttaylor").uniqueResult();
        assertNotNull(user);
        assertNotNull(user.getId());
        assertEquals("Tim", user.getFirstName());
        log.info("User: " + user);
        
        // Retrieve via Id
        long id = user.getId();
        User user2 = session.get(User.class, id);
        assertEquals(user, user2);
        
        // Retrieve collection
        List<User> users = (List<User>) session.createQuery("FROM User u").getResultList();
        assertEquals(1, users.size());
        
        // Update
        user.setUserType(UserType.OWNER);
        session.saveOrUpdate(user);
        assertEquals(new Long(id), user.getId()); // still same Id
        
        // Delete
        session.delete(user);
        assertEquals(0, session.createQuery("FROM User u").getResultList().size());      
    }
}
