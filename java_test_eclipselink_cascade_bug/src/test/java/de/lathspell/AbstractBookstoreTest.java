package de.lathspell;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

@Ignore
public abstract class AbstractBookstoreTest {

    protected static EntityManager em;
    protected static EntityTransaction et;

    abstract String getPersistenceUnitName();

    @Before
    public void before() throws ClassNotFoundException {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(getPersistenceUnitName());
        assertNotNull(factory);
        em = factory.createEntityManager();
        assertNotNull(em);
        et = em.getTransaction();
        assertNotNull(et);

        // fixtures();
    }

    @After
    public void after() {
        if (et != null && et.isActive()) {
            et.rollback(); // failed assertion in one of the tests
        }
        if (em != null) {
            em.close();
        }
    }

    @Test
    public void testDummy() {
        assertTrue(true);
    }
}
