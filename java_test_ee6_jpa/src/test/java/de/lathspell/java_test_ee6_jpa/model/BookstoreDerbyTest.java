package de.lathspell.java_test_ee6_jpa.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BookstoreDerbyTest extends BookstoreTemplate {

    @Override
    protected Pu getPersistenceUnitName() {
        return Pu.derbyJavaTestJpaPU;
    }

    @Test
    @Override
    public void testReturnValue() {
        assertEquals(Integer.class, em.createNativeQuery("SELECT count(*) FROM books").getSingleResult().getClass());
        // workaround: ((Number) em.createNativeQuery("SELECT count(*) FROM books").getSingleResult()).longValue())
    }
}
