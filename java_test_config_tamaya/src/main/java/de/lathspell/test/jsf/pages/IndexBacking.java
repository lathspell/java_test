package de.lathspell.test.jsf.pages;

import org.apache.tamaya.inject.ConfigurationInjector;
import org.apache.tamaya.inject.ConfiguredProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IndexBacking {

    private static final Logger log = LoggerFactory.getLogger(IndexBacking.class);

    @ConfiguredProperty(keys = "greeting")
    private String greeting;

    @ConfiguredProperty
    private String version;

    public String getGreeting() {
        return greeting;
    }

    public String getVersion() {
        return version;
    }

}
