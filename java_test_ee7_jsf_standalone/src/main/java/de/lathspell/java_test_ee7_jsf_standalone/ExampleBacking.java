package de.lathspell.java_test_ee7_jsf_standalone;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class ExampleBacking {

    public String getStatus() {
        return "works!";
    }
}
