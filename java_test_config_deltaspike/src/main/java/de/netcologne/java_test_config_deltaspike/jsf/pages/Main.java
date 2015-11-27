package de.netcologne.java_test_config_deltaspike.jsf.pages;

import org.apache.deltaspike.core.api.config.ConfigResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        log.info("main");
        
        // This variable is then used in the application
        int version = ConfigResolver.resolve("version").as(Integer.class).withDefault(-1).getValue();
        
        // Example usage
        log.info("Starting version " + version);

        System.exit(0); // To end config file watcher
    }

}
