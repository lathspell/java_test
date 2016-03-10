package de.lathspell.test.springboot.web.rest;

import de.lathspell.test.springboot.model.Greeting;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_HTML;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import org.springframework.stereotype.Component;

/**
 * This is a JAX-RS resource.
 */
@Component
@Path("/greeter")
public class JaxrsGreetingResource {

    @GET
    @Produces(TEXT_HTML)
    public String home() {
        return "<p>Hello World!</p>";
    }

    @GET
    @Path("/hello/{name}")
    @Produces(APPLICATION_JSON)
    public Greeting name(@PathParam("name") String name) {
        return new Greeting(null, name);
    }

    @GET
    @Path("/hello2/{name}")
    @Produces(TEXT_PLAIN)
    public String nameWithSalutation(@PathParam("name") String name, @QueryParam("salutation") @DefaultValue("Dr.") String salutation) {
        return new Greeting(salutation, name).toString();
    }
}
