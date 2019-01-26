package de.lathspell.test.jsf.pages;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.apache.tamaya.inject.api.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@RequestScoped
public class IndexBacking {

    private static final Logger log = LoggerFactory.getLogger(IndexBacking.class);

    @Config("greeting")
    private String greeting;

    @Config(value = "version", defaultValue = "1.0-AnnotatedDefaultValue")
    private String version;

    @PostConstruct
    public void postConstruct() {
        log.info("");
    }
    
    public String getGreeting() {
        return greeting;
    }

    public String getVersion() {
        return version;
    }

}
