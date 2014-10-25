package de.lathspell.test.cdi;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

public class GreeterTest extends MyArquillianTemplate {

    @Inject
    Greeter greeter;

    @Test
    public void testGreeter() {
        Assert.assertEquals("Hello world!", greeter.greet("world"));
        Assert.assertEquals("Hello world!", greeter.greet("world"));
    }
}