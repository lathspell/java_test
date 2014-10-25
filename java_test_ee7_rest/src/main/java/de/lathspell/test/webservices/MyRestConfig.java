package de.lathspell.test.webservices;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ServerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationPath("rest")
public class MyRestConfig extends Application {

    private static final Logger log = LoggerFactory.getLogger(MyRestConfig.class);

    @Override
    public Set<Object> getSingletons() {
        Set<Object> singletons = new HashSet<>();
        log.info("Enabling custom Jackson JSON provider");
        singletons.add(new JacksonJsonProvider() /* .configure(SerializationFeature.INDENT_OUTPUT, true) */);
        singletons.add(new LoggingFilter(java.util.logging.Logger.getLogger("de.lathspell.myrest.trace"), 10*1024));
        return singletons;
    }

    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> properties = new HashMap<>();
        log.info("Disabling default MOXy JSON provider");
        // properties.put("jersey.config.disableMoxyJson.server", true);
        properties.put(ServerProperties.MOXY_JSON_FEATURE_DISABLE, true);
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
        resources.add(com.fasterxml.jackson.jaxrs.base.JsonMappingExceptionMapper.class);
        resources.add(com.fasterxml.jackson.jaxrs.base.JsonParseExceptionMapper.class);
        resources.add(com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider.class);
        resources.add(com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider.class);
        resources.add(com.fasterxml.jackson.jaxrs.json.JsonMappingExceptionMapper.class);
        resources.add(com.fasterxml.jackson.jaxrs.json.JsonParseExceptionMapper.class);
        resources.add(de.lathspell.test.webservices.MyRestExceptionMapper.class);
        resources.add(de.lathspell.test.webservices.MyRestResource.class);
        resources.add(de.lathspell.test.webservices.exceptions.ApiProblemJsonMessageBodyReader.class);
        resources.add(de.lathspell.test.webservices.exceptions.ApiProblemJsonMessageBodyWriter.class);
        resources.add(org.glassfish.jersey.server.wadl.internal.WadlResource.class);
    }

}
