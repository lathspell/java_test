package de.lathspell.test.plain_jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import lombok.extern.slf4j.Slf4j;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import de.lathspell.test.config.AppConfiguration;

/**
 * Example how to use JPA with transaction type RESOURCE_LOCAL.
 * 
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("postgres") // "h2" or "postgres"
@ContextConfiguration(classes = AppConfiguration.class)
@Slf4j
public class JpaTransactionsTest {

    @Autowired
    private EntityManagerFactory emf;

    @Test
    public void testResourceLocalTransaction() {
        Integer result = null;
        
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            
            result = (Integer) em.createNativeQuery("SELECT 42").getSingleResult();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        
        assertThat(result, is(42));
    }

}
