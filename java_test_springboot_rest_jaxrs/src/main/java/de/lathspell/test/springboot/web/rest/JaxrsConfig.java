package de.lathspell.test.springboot.web.rest;

import javax.ws.rs.ApplicationPath;

import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.wadl.internal.WadlResource;
import org.springframework.stereotype.Component;

@Component
@ApplicationPath("/rest/jaxrs")
@Slf4j
public class JaxrsConfig extends ResourceConfig {

    public JaxrsConfig() {
        log.info("ctor");
        registerEndpoints();
    }

    private void registerEndpoints() {
        register(WadlResource.class);
        register(JaxrsGreetingResource.class);
    }
}
