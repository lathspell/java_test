package de.lathspell.test.rest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import lombok.extern.slf4j.Slf4j;

@ApplicationPath("rest")
@Slf4j
public class MyRestConfig extends Application {

    @Override
    public Set<Object> getSingletons() {
        Set<Object> singletons = new HashSet<>();
        log.info("Enabling custom Jackson JSON provider");
        singletons.add(new JacksonJsonProvider() /* .configure(SerializationFeature.INDENT_OUTPUT, true) */);
        return singletons;
    }

    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> properties = new HashMap<>();
        log.info("Disabling default MOXy JSON provider");
        properties.put("jersey.config.disableMoxyJson.server", true); // Glassfish-only
        return properties;
    }

    @Override
    public Set<Class<?>> getClasses() {
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
        resources.add(de.lathspell.test.rest.resources.MyRestResource.class);
    }

}
