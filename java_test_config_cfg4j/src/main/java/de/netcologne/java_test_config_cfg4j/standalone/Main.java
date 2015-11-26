package de.netcologne.java_test_config_cfg4j.standalone;

import org.cfg4j.provider.ConfigurationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.netcologne.java_test_config_cfg4j.config.ConfigurationProviderProvider;


public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        log.info("main");
        
        // originally intended to be used with CDI; loads configuration
        ConfigurationProviderProvider provProv = new ConfigurationProviderProvider();
        provProv.postConstruct();
        
        // This variable is then used in the application
        ConfigurationProvider prov = provProv.getConfigurationProvider();
        
        // Example usage
        log.info("Starting version " + prov.getProperty("version", String.class));

        System.exit(0); // To end config file watcher
    }

}
