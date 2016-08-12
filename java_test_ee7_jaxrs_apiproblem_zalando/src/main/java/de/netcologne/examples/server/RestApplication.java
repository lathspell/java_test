package de.netcologne.examples.server;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import lombok.extern.slf4j.Slf4j;
import org.zalando.problem.ProblemModule;

import de.netcologne.examples.server.logging.Slf4jRestLoggingFilter;

@ApplicationPath("/rest")
@Slf4j
public class RestApplication extends Application {

    public RestApplication() {
        log.info("ctor");
    }

    @Override
    public Set<Object> getSingletons() {
        Set<Object> singletons = new HashSet<>();
        singletons.add(new Slf4jRestLoggingFilter("server"));
        singletons.add(new ObjectMapper().registerModules(new Jdk8Module(), new ProblemModule()));
        return singletons;
    }

    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> properties = new HashMap<>();
        log.info("Disabling MOXy JSON provider");
        properties.put("jersey.config.server.disableMoxyJson", true); // if Glassfish is used
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
        resources.add(de.netcologne.examples.server.resources.CalcResource.class);
    }
}
