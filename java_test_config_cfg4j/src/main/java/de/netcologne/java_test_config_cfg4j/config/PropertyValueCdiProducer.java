package de.netcologne.java_test_config_cfg4j.config;

import java.util.NoSuchElementException;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import org.cfg4j.provider.ConfigurationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Dependent
public class PropertyValueCdiProducer {

    private static final Logger log = LoggerFactory.getLogger(PropertyValueCdiProducer.class);

    @Inject
    private ConfigurationProvider prov;

    @Produces
    @PropertyValue
    public boolean producePropertyValueBoolean(InjectionPoint ip) {
        log.info("producing boolean for " + ip);
        try {
            log.info("produced boolean " + producePropertyValue(ip));
            return Boolean.parseBoolean(producePropertyValue(ip));
        } catch (Exception e) {
            throw new RuntimeException("Cannot convert config variable: " + ip);
        }
    }

    @Produces
    @PropertyValue
    public int producePropertyValueInt(InjectionPoint ip) {
        try {
            return Integer.parseInt(producePropertyValue(ip));
        } catch (Exception e) {
            throw new RuntimeException("Cannot convert value '" + producePropertyValue(ip) + "' of config variable: " + ip);
        }
    }

    @Produces
    @PropertyValue
    public String producePropertyValue(InjectionPoint ip) {
        PropertyValue pv = ip.getAnnotated().getAnnotation(PropertyValue.class);
        String value;
        try {
            value = prov.getProperty(pv.value(), String.class);
        } catch (NoSuchElementException e) {
            value = pv.defaultValue();
        }
        if (value == null) {
            throw new RuntimeException("Property " + pv.value() + " (with default value '" + pv.defaultValue() + "') not found.");
        }
        return value;
    }
}
