package de.lathspell.test.model;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Person {

    private String name = "Nobody";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
