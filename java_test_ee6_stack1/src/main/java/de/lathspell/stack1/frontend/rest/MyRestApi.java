package de.lathspell.stack1.frontend.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;

import de.lathspell.stack1.frontend.rest.exceptions.ApiProblemException;
import de.lathspell.stack1.utils.Loggable;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

/**
 * REST Web Service
 *
 * Without @RequestScoped, @Inject does not work!
 *
 * @see LoggerInterceptor
 */
@Path("/MyRestApi")
@Loggable
@RequestScoped
public class MyRestApi {

    @Context
    private UriInfo context;

    @Inject
    Logger log;

    @GET
    @Path("/hello/{name}")
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    public String hello(@PathParam("name") String name) throws Exception {
        log.info("BAR");
        return "Hello " + name;
    }

    @GET
    @Path("/apiProblem")
    @Produces(APPLICATION_JSON)
    public String apiProblem() {
        try {
            throw new IllegalArgumentException("Give me something else!");
        } catch (Exception e) {
            throw new ApiProblemException(BAD_REQUEST, e);
        }
    }

    @GET
    @Path("/exception")
    @Produces(APPLICATION_JSON)
    public String exception() {
        throw new NullPointerException("Zeeerooo!");
    }

}
