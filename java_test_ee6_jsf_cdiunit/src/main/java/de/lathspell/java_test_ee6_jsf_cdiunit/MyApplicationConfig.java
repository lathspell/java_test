package de.lathspell.java_test_ee6_jsf_cdiunit;

import javax.enterprise.inject.Produces;

public class MyApplicationConfig {

    @Produces
    public MyConverter produceMyConverter() {
        return new MyConverter(true);
    }
}
