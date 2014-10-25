package de.lathspell.java_test_ee6_jpa.model;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Query;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ArrayExamplePostgresTest extends Template {

    @Override
    protected Template.Pu getPersistenceUnitName() {
        return Template.Pu.postgresJavaTestJpaPU;
    }

    @Test
    public void testArrays() {
        em.createQuery("DELETE FROM ArrayExamplePostgres");

        // create object
        ArrayExamplePostgres aep = new ArrayExamplePostgres();
        aep.setId(4);
        aep.setParams(Arrays.asList("one", "two", "three"));

        // persist object
        et.begin();
        em.persist(aep);
        em.flush();
        em.refresh(aep);
        et.commit();

        //
        // check if object exists in database
        //
        Query query = em.createNativeQuery("SELECT * FROM array_example WHERE params @> ARRAY['two']", ArrayExamplePostgres.class);
        List<ArrayExamplePostgres> result = query.getResultList();
        assertEquals(1, result.size());
        assertEquals("4", result.get(0).getId());
    }
}
