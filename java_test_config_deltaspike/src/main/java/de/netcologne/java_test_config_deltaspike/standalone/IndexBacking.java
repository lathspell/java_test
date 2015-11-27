package de.netcologne.java_test_config_deltaspike.standalone;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.deltaspike.core.api.config.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@RequestScoped
public class IndexBacking {

    private static final Logger log = LoggerFactory.getLogger(IndexBacking.class);

    @Inject
    @ConfigProperty(name = "version", defaultValue = "DEVEL")
    private String version;

    @PostConstruct
    public void postConstruct() {
        log.info("");
    }

    public String getVersion() {
        return version;
    }

}
