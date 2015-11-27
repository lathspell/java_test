package de.netcologne.java_test_config_deltaspike.config;

import org.apache.deltaspike.core.api.config.PropertyFileConfig;

public class MyResourcesConfigSources implements PropertyFileConfig {

    @Override
    public String getPropertyFileName() {
        return "application.properties";
    }

    @Override
    public boolean isOptional() {
        return false;
    }

}
