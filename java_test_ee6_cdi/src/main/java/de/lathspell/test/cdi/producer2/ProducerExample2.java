package de.lathspell.test.cdi.producer2;

import javax.ejb.Stateless;
import javax.inject.Inject;

/** The bean into which one of the Greeter implementations is injected. */
@Stateless
public class ProducerExample2 {

    /**
     * @see Factory.
     */
    @Inject
    Greeting greeting;

    public String sayHello() {
        return greeting.sayHello();
    }
}
