package de.lathspell.test.cdi;

import javax.inject.Inject;

import static org.junit.Assert.*;
import org.junit.Test;

public class AlternativesExampleTest extends MyArquillianTemplate {

    @Inject
    AlternativesExample example;

    @Test
    public void testAlternatives() {
        assertEquals("Hallo Welt!", example.getGreeting("Welt"));
    }
}
