package de.lathspell.test.cdi.spring.greeter;

public abstract class AbstractGreeter {

    private String name;

    public abstract String getGreeting();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
