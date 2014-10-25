package de.lathspell.test.cdi.producer;

public class ProducedGreeterEnglish implements ProducedGreeter {

    @Override
    public String getGreeting() {
        return "Hello";
    }
}
