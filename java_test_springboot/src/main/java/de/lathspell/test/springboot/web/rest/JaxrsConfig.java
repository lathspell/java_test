package de.lathspell.test.springboot.web.rest;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.wadl.internal.WadlResource;
import org.springframework.stereotype.Component;

@Component
@ApplicationPath("/rest/jaxrs")
public class JaxrsConfig extends ResourceConfig {

    public JaxrsConfig() {
        registerEndpoints();
    }

    private void registerEndpoints() {
        register(JaxrsGreetingResource.class);
    }
}
