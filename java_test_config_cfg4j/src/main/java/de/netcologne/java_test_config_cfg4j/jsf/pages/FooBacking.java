package de.netcologne.java_test_config_cfg4j.jsf.pages;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.netcologne.java_test_config_cfg4j.config.PropertyValue;

@Named
@RequestScoped  // <- so that config changes are can be considered
public class FooBacking {

    private static final Logger log = LoggerFactory.getLogger(FooBacking.class);

    @Inject
    @PropertyValue("module.foo.active")
    private boolean isActive;

    @PostConstruct
    public void postConstruct() {
        log.info("");
    }

    public boolean isIsActive() {
        return isActive;
    }

}
