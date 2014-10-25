package de.lathspell.test.cdi;

import javax.inject.Inject;

import static org.junit.Assert.*;
import org.junit.Test;

public class ResourceExampleTest extends MyArquillianTemplate {

    @Inject
    ResourceExample resourceExample;

    @Test
    public void testResources() throws Exception {
        assertEquals("green", resourceExample.myFirstColour);
    }
}
