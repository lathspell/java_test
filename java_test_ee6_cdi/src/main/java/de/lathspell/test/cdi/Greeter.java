package de.lathspell.test.cdi;

import javax.inject.Inject;

import de.lathspell.test.cdi.helper.LowercaseTranslator;

/**
 * Most simplest example of CDI.
 *
 * @Inject looks for a class named LowercaseTranslator, creates an instance
 * using the no-args constructor and assigns it to the translator attribute.
 */
public class Greeter {

    @Inject
    LowercaseTranslator translator;

    public String greet(String name) {
        return "Hello " + translator.translate(name) + "!";
    }
}