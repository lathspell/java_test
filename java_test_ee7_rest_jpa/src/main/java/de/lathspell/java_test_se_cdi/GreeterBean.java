package de.lathspell.java_test_se_cdi;

import javax.inject.Inject;

import org.slf4j.Logger;

import de.lathspell.java_test_se_cdi.config.PropertyValue;

public class GreeterBean {

    @Inject
    Logger log;

    @Inject
    @PropertyValue("greeter.name")
    private String name;

    public String getGreeting() {
        log.debug("entering getGreeting()");
        return "Hello " + name;
    }
}
