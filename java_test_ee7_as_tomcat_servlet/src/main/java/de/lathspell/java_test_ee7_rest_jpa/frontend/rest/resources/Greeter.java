package de.lathspell.java_test_ee7_rest_jpa.frontend.rest.resources;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

import lombok.extern.slf4j.Slf4j;

@RequestScoped
@Path("/greeter")
@Slf4j
public class Greeter {

    public Greeter() {
        log.info("ctor");
    }

    @GET
    @Produces(TEXT_PLAIN)
    public String helloDefault() {
        log.info("helloDefault called");
        return "Hello you!";
    }

    @GET
    @Path("/{name: .*}")
    @Produces(TEXT_PLAIN)
    public String hello(@PathParam("name") String name) {
        log.info("hello called with prop=" + name);
        return "Hello " + name;
    }
}
