package de.lathspell.test.webservices;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.filter.LoggingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configures the REST application.
 *
 * Remember:
 * "When published in a Servlet container, the value of the application path may be overridden using a servlet-mapping element in the web.xml."
 */
@ApplicationPath("thePathIsOverriddenInGrizzlyBootstrap")
public class MyRestApp extends Application {

    private static final Logger log = LoggerFactory.getLogger(MyRestApp.class);

    @Override
    public Map<String, Object> getProperties() {
        log.info("Configuring REST properties");
        Map<String, Object> properties = new HashMap<>();
        properties.put("jersey.config.server.tracing.type", "ALL");
        return properties;

    }

    @Override
    public Set<Object> getSingletons() {
        log.info("Configuring REST singletons");
        Set<Object> singletons = new HashSet<>();
        singletons.add(new LoggingFilter(java.util.logging.Logger.getLogger(MyRestApp.class.getName()), true));
        return singletons;
    }

    @Override
    public Set<Class<?>> getClasses() {
        log.info("Configuring REST classes");
        Set<Class<?>> classes = new java.util.HashSet<>();
        addRestResourceClasses(classes);
        return classes;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.fasterxml.jackson.jaxrs.base.JsonMappingExceptionMapper.class);
        resources.add(com.fasterxml.jackson.jaxrs.base.JsonParseExceptionMapper.class);
        resources.add(com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider.class);
        resources.add(com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider.class);
        resources.add(com.fasterxml.jackson.jaxrs.json.JsonMappingExceptionMapper.class);
        resources.add(com.fasterxml.jackson.jaxrs.json.JsonParseExceptionMapper.class);
        resources.add(de.lathspell.test.webservices.MyRestResource.class);
        resources.add(de.lathspell.test.webservices.MyRestExceptionMapper.class);
        resources.add(org.glassfish.jersey.server.wadl.internal.WadlResource.class);
    }

}
