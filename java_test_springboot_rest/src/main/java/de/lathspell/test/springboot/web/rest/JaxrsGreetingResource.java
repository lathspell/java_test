package de.lathspell.test.springboot.web.rest;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import org.springframework.stereotype.Component;

import de.lathspell.test.springboot.model.Greeting;


/**
 * This is a JAX-RS resource.
 */
@Component
@Path("/greeter")
public class JaxrsGreetingResource {

    @GET
    public String home() {
        return "Hello World!";
    }

    @GET
    @Path("/hello/{name}")
    @Produces(APPLICATION_JSON)
    public Greeting name(@PathParam("name") String name) {
        return new Greeting(null, name);
    }

    @GET
    @Path("/hello2/{name}")
    public String nameWithSalutation(@PathParam("name") String name, @QueryParam("salutation") @DefaultValue("Dr.") String salutation) {
        return new Greeting(salutation, name).toString();
    }
}
