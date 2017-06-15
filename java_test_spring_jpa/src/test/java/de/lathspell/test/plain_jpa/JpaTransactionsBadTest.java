package de.lathspell.test.plain_jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import lombok.extern.slf4j.Slf4j;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import de.lathspell.test.config.AppConfiguration;

/**
 * Wrong usage.
 * 
 * The @PersistenceContext annotation is for JPA with transaction mode JTA.
 * In this mode, transaction objects have to be provided by the Application Server
 * as UserTransaction instances, trying to aquire them with em.getTransaction() is wrong.
 * 
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("postgres") // "h2" or "postgres"
@ContextConfiguration(classes = AppConfiguration.class)
@Slf4j
public class JpaTransactionsBadTest {

    @PersistenceContext// (unitName = "entityManagerFactory") // Not "userPU" as set inside JpaConfiguration.entityManagerFactory() !!!
    private EntityManager em;

    @Test
    public void testDependencyInjection() {
        assertNotNull(em);
    }

    /**
     * Wrongly mixing Spring and JPA.
     *
     * "Not allowed to create transaction on shared EntityManager - use Spring transactions or EJB CMT instead"
     */
    @Test(expected = IllegalStateException.class)
    public void testBad() {
        em.getTransaction();
    }

}
