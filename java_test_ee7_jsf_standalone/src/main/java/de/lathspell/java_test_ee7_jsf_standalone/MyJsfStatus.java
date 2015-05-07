package de.lathspell.java_test_ee7_jsf_standalone;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class MyJsfStatus {

    public String getStatus() {
        java.util.logging.Logger.getLogger(MyJsfStatus.class.getSimpleName()).info("getStatus");
        return "works!";
    }
}
