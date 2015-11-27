package de.lathspell.test.standalone;

import java.util.TreeSet;

import org.apache.tamaya.Configuration;
import org.apache.tamaya.ConfigurationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        org.slf4j.bridge.SLF4JBridgeHandler.removeHandlersForRootLogger();
        org.slf4j.bridge.SLF4JBridgeHandler.install();

        Configuration cfg = ConfigurationProvider.getConfiguration();

        log.info("All props:");
        new TreeSet<>(cfg.getProperties().keySet()).stream().forEach((key) -> {
            log.info("  {}: {}", key, cfg.get(key));
        });

        log.info("Environment variable USER: " + cfg.getOrDefault("USER", "- no environment variable USER found -"));
        log.info("Version as String: " + cfg.get("version"));
        log.info("Version as int: " + cfg.getOrDefault("version", Integer.class, -1));
        log.info("Default Value via Optional: " + cfg.getOrDefault("doesnotexist", "SomeDefaultValue"));
        log.info("Module foo.isActive: " + cfg.getOrDefault("foo.isActive", "-"));
        log.info("Overridden: " + cfg.getOrDefault("overridden", "-"));
    }
}
