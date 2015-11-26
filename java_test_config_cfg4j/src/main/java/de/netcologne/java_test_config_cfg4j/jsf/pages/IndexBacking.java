package de.netcologne.java_test_config_cfg4j.jsf.pages;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.netcologne.java_test_config_cfg4j.config.PropertyValue;

@Named
@RequestScoped
public class IndexBacking {

    private static final Logger log = LoggerFactory.getLogger(IndexBacking.class);

    @Inject
    @PropertyValue(value = "version", defaultValue = "DEVEL")
    private String version;

    @PostConstruct
    public void postConstruct() {
        log.info("");
    }

    public String getVersion() {
        return version;
    }

}
