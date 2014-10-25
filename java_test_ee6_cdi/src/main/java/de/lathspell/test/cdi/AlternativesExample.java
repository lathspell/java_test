package de.lathspell.test.cdi;

import javax.inject.Inject;
import de.lathspell.test.cdi.helper.LocalizedGreeter;

public class AlternativesExample {

    @Inject
    LocalizedGreeter greeter;

    public String getGreeting(String name) {
        return greeter.getGreeting() + " " + name + "!";
    }
}
