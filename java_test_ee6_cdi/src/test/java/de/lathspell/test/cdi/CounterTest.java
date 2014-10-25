package de.lathspell.test.cdi;

import javax.inject.Inject;

import org.junit.Test;

import static org.junit.Assert.*;

public class CounterTest extends MyArquillianTemplate {

    @Inject
    Counter counter1;
    @Inject
    Counter counter2;
    @Inject
    Counter counter3;

    @Test
    public void testCounter() {
        assertNotSame(counter1.rcounter, counter2.rcounter);

        // Using one instance
        assertEquals(1, counter1.getRequestCounter());
        assertEquals(2, counter1.getRequestCounter());

        // Other instances are unaffected
        assertEquals(1, counter2.getRequestCounter());
        assertEquals(1, counter3.getRequestCounter());

        // Multiple @Inject assigned the same instance
        assertSame(counter1.scounter, counter2.scounter);
        assertEquals(1, counter1.getSingletonCount());
        assertEquals(2, counter2.getSingletonCount());
        assertEquals(3, counter3.getSingletonCount());
    }
}
