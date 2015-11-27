package de.lathspell.test.jsf.pages;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.apache.tamaya.inject.api.Config;
import org.apache.tamaya.inject.api.ConfigDefault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@RequestScoped
public class IndexBacking {

    private static final Logger log = LoggerFactory.getLogger(IndexBacking.class);

    @Config("greeting")
    private String greeting;

    @Config("version")
    @ConfigDefault("1.0-AnnotatedDefaultValue")
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
