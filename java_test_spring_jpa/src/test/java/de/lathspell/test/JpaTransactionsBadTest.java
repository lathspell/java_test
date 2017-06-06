package de.lathspell.test;

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

@RunWith(SpringRunner.class)
@ActiveProfiles("postgres") // "h2" or "postgres"
@ContextConfiguration(classes = AppConfiguration.class)
@Slf4j
public class JpaTransactionsBadTest {

    @PersistenceContext// (unitName = "entityManagerFactory") // Not "userPU" as set inside JpaConfiguration.entityManagerFactory() !!!
    private EntityManager entityManager;

    @Test
    public void testDependencyInjection() {
        assertNotNull(entityManager);
    }

    /**
     * Wrongly mixing Spring and JPA.
     *
     * "Not allowed to create transaction on shared EntityManager - use Spring transactions or EJB CMT instead"
     */
    @Test(expected = IllegalStateException.class)
    public void testBad() {
        entityManager.getTransaction();
    }

}
