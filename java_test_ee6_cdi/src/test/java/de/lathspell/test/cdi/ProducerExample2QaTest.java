package de.lathspell.test.cdi;

import javax.inject.Inject;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

import de.lathspell.test.cdi.producer2.GreetingFactory;
import de.lathspell.test.cdi.producer2.ProducerExample2;

public class ProducerExample2QaTest extends MyArquillianTemplate {

    @BeforeClass
    public static void setUpEnv() {
        GreetingFactory.env = "qa";
    }

    @Inject
    ProducerExample2 producerExample;

    @Test
    public void testProducer() throws Exception {
        assertEquals("Hello tester", producerExample.sayHello());
    }
}
