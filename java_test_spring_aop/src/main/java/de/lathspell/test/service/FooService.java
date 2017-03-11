package de.lathspell.test.service;

import org.springframework.stereotype.Service;

@Service
public class FooService {

    public String giveMeFoo() {
        return "foo";
    }

    public String giveMe(String firstName) {
        return firstName;
    }

    public String hello(String firstName, String lastName) {
        return "Hello " + firstName + " " + lastName;
    }

    public String defaultHello() {
        return hello("No", "Name");
    }

    String giveMePackage() {
        return "package";
    }

    protected String giveMeProtected() {
        return "protected";
    }
}
