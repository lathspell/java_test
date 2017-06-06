package de.lathspell.test;

import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import lombok.extern.slf4j.Slf4j;
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
public class JpaTransactionsBad2Test {

    /**
     * Wrongly mixing Spring and JPA.
     *
     * "No Persistence provider for EntityManager named localUserPU"
     * 
     * Probably because it's trying to use JNDI but I don't really know.
     */
    @Test(expected = PersistenceException.class)
    public void testBad() {
        Persistence.createEntityManagerFactory("localUserPU");
    }

}
