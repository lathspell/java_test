package de.lathspell.test.cdi.producer;

import de.lathspell.test.cdi.helper.PreferredProducedGreeter;
import javax.inject.Inject;


public class ProducerExample {

    /**
     * Calls ProducedGreeterFactory.createGreeter() to create an Instance.
     *
     * That method is chosen because there is no class in the classpath
     * that implements @PreferredProducedGreeter nor is there any other
     * method that has a @Produces annotation and returns this type.
     *
     * Without our self-made @PreferredProducerGreeter annotation, CDI can't
     * decide between the two matching ProducedGreeterGerman and
     * ProducedGreeterEnglish classes and the @Produces method. All three would
     * suffice. (Does that mean that @Produce always needs a custom annotation?
     */
    @Inject @PreferredProducedGreeter
    ProducedGreeter greeter;

    public String getGreeting(String name) {
        return greeter.getGreeting() + " " + name + "!";
    }
}
