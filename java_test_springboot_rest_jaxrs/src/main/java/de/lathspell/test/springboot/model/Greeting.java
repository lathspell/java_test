package de.lathspell.test.springboot.model;

import lombok.Value;

@Value
public class Greeting {

    private final String salutation;
    private final String name;

    @Override
    public String toString() {
        return "Hello " + (salutation == null ? "" : (salutation + " ")) + name + "!";
    }

}
