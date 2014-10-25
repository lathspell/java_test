package de.lathspell.stack1.frontend.rest;

import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/rest")
public class MyRestConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        // following code can be used to customize Jersey 1.x JSON provider:
        try {
            Class jacksonProvider = Class.forName("org.codehaus.jackson.jaxrs.JacksonJsonProvider");
            resources.add(jacksonProvider);
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(de.lathspell.stack1.frontend.rest.MyRestApi.class);
        resources.add(de.lathspell.stack1.frontend.rest.exceptions.ApiProblemExceptionMapper.class);
        resources.add(de.lathspell.stack1.frontend.rest.exceptions.ApiProblemJsonMessageBodyReader.class);
        resources.add(de.lathspell.stack1.frontend.rest.exceptions.ApiProblemJsonMessageBodyWriter.class);
    }

}
