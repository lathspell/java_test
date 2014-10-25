package de.lathspell.test.cdi.producer;

public class ProducedGreeterGerman implements ProducedGreeter {

    @Override
    public String getGreeting() {
        return "Hallo";
    }
}
