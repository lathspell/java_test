package de.lathspell.java_test_ee6_jpa.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;

import static org.junit.Assert.assertNotNull;

/**
 * Template for most other test cases.
 *
 * Cannot be abstract as then JUnit complains.
 */
@Ignore
public abstract class Template {

    public static enum Pu {

        mysqlJavaTestJpaPU,
        postgresJavaTestJpaPU,
        derbyJavaTestJpaPU;

    }
    protected EntityManager em;

    protected EntityTransaction et;

    @Before
    public void before() throws ClassNotFoundException {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(getPersistenceUnitName().name());
        assertNotNull(factory);
        em = factory.createEntityManager();
        assertNotNull(em);
        et = em.getTransaction();
        assertNotNull(et);

        fixtures();
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

    protected Pu getPersistenceUnitName() {
        return Pu.mysqlJavaTestJpaPU;
    }

    protected void fixtures() {
    }

    protected void dumpResultSet(ResultSet rs) throws SQLException {
        for (int i = 1; i < rs.getMetaData().getColumnCount(); i++) {
            System.out.format("%s = %s\n", rs.getMetaData().getColumnLabel(i), rs.getString(i));
        }
    }
}
