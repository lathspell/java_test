package de.lathspell.test.cdi.spring.greeter;

public class EnglishGreeter extends AbstractGreeter {

    @Override
    public String getGreeting() {
        return "Hello " + getName();
    }
}
