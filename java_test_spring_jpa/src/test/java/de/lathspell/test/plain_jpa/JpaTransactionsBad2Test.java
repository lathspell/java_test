package de.lathspell.test.plain_jpa;

import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import de.lathspell.test.config.AppConfiguration;

/**
 * Wrong usage.
 * 
 * In JPA transaction mode "RESOURCE_LOCAL", an EntityManagerFactory has to
 * be injected using @PersistenceUnit, not created manually.
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("postgres") // "h2" or "postgres"
@ContextConfiguration(classes = AppConfiguration.class)
@Slf4j
public class JpaTransactionsBad2Test {

    /**
     * "No Persistence provider for EntityManager named localUserPU".
     */
    @Test(expected = PersistenceException.class)
    public void testBad() {
        Persistence.createEntityManagerFactory("localUserPU");
    }

}
