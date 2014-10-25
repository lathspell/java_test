package de.lathspell.test.cdi.spring.greeter;

public class GermanGreeter extends AbstractGreeter {

    @Override
    public String getGreeting() {
        return "Hallo " + getName();
    }
}
