package de.lathspell.test.lang.optional;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class OptionalTest {

    @Test
    public void test() {
        Optional<String> o = Optional.of("test");
        assertTrue(o.isPresent());
        assertEquals("test", o.get());
        assertEquals("test", o.orElse("else"));

        Optional<String> oNull = Optional.ofNullable(null);
        assertFalse(oNull.isPresent());
        Exception caught = null;
        try {
            oNull.get();
        } catch (NoSuchElementException e) {
            caught = e;
        }
        assertNotNull(caught);
        assertEquals("else", oNull.orElse("else"));
        
        Optional<String> oEmpty = Optional.empty();
        assertFalse(oEmpty.isPresent());
        caught = null;
        try {
            oEmpty.get();
        } catch (NoSuchElementException e) {
            caught = e;
        }
        System.out.println(caught);
        assertNotNull(caught);
        assertEquals("else", oEmpty.orElse("else"));
    }
}
