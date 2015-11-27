package de.lathspell.test;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import org.apache.tamaya.Configuration;
import org.apache.tamaya.ConfigurationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class MyConfigurationProducer {

    private static final Logger log = LoggerFactory.getLogger(MyConfigurationProducer.class);

    @PostConstruct
    public void postConstruct() {
        // Kill JUL. Does not really belong here.
        org.slf4j.bridge.SLF4JBridgeHandler.removeHandlersForRootLogger();
        org.slf4j.bridge.SLF4JBridgeHandler.install();

        log.info("");
    }

    @Produces
    public Configuration produceConfiguration() {
        log.info("");
        return ConfigurationProvider.getConfiguration();
    }

}
