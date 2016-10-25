package de.lathspell.java_test_ee7_rest_jpa.frontend.rest;

import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import lombok.extern.slf4j.Slf4j;

@ApplicationPath("/rest")
@Slf4j
public class ApplicationConfig extends Application {

    public ApplicationConfig() {
        log.info("ctor");
    }

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method. It is automatically
     * populated with all resources defined in the project. If required, comment
     * out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(de.lathspell.java_test_ee7_rest_jpa.frontend.rest.resources.ArticleFacade.class);
        resources.add(de.lathspell.java_test_ee7_rest_jpa.frontend.rest.resources.Greeter.class);
    }

}
