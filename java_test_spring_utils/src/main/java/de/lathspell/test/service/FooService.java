package de.lathspell.test.service;

import org.springframework.util.Assert;


public class FooService {

    public static String getGreeting(String name) {
        Assert.notNull(name, "Name may not be NULL!");
        return "Hello " + name;
    }
}
