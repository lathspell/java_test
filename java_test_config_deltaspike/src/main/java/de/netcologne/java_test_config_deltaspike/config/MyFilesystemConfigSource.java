package de.netcologne.java_test_config_deltaspike.config;

import java.util.Map;

import org.apache.deltaspike.core.api.config.PropertyFileConfig;
import org.apache.deltaspike.core.spi.config.ConfigSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyFilesystemConfigSource implements PropertyFileConfig {

    private static final Logger log = LoggerFactory.getLogger(MyFilesystemConfigSource.class);

    public MyFilesystemConfigSource() {
    }

    @Override
    public String getPropertyFileName() {
        return "/tmp/local.properties";
    }

    @Override
    public boolean isOptional() {
        return true;
    }

}
