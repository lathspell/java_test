package de.lathspell.test.cdi;

import javax.inject.Inject;

import static org.junit.Assert.*;
import org.junit.Test;

import de.lathspell.test.cdi.producer.ProducerExample;

public class ProducerExampleTest extends MyArquillianTemplate {

    @Inject
    ProducerExample example1;
    @Inject
    ProducerExample example2;

    @Test
    public void testProducer() {
        assertEquals("Hallo x!", example1.getGreeting("x"));
        assertEquals("Hello x!", example2.getGreeting("x"));
    }
}
