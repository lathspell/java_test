package de.lathspell.java_test_mockito;

import javax.inject.Inject;

public class Foo {

    @Inject
    FooHelper fooHelper;

    public String getHello() {
        return "hello";
    }

    public String getGreeting(String name) {
        return "Hello " + name + "!";
    }

    public String getUserMsg(String msg) {
        return fooHelper.getUser() + ": " + msg;
    }
}
