package de.lathspell.java_test_ee7_jpa.model;

import javax.validation.ConstraintViolationException;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class NotNullExampleTest extends Template {

    @Test
    public void testNotNull() throws Exception {
        NotNullExample obj = new NotNullExample();

        obj.a = null; // @Basic(optional = false) has no effect!
        obj.b = "b"; // due to @NotNull
        obj.c = "c"; // due to @Column(nullable = false)
        obj.d = "d"; // due to @NotNull

        assertNull(obj.a);
        assertNotNull(obj.b);
        assertNotNull(obj.c);
        assertNotNull(obj.d);

        et.begin();
        try {
            em.persist(obj);
            em.flush();
            em.refresh(obj);
        } catch (ConstraintViolationException e) {
            throw new Exception(e + ": " + e.getConstraintViolations(), e);
        }
        et.commit();

        assertNull(obj.a);
        assertNotNull(obj.b);
        assertNotNull(obj.c);
        assertNotNull(obj.d);
    }
}
