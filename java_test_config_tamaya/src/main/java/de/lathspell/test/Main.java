package de.lathspell.test;

import org.apache.tamaya.Configuration;
import org.apache.tamaya.ConfigurationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Configuration cfg = ConfigurationProvider.getConfiguration();
        log.info("All props:\n" + cfg.getProperties());
    }
}
