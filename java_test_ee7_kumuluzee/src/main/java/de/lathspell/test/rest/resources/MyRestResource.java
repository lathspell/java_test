package de.lathspell.test.rest.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

import lombok.extern.slf4j.Slf4j;

@Path("/myrest")
@Slf4j
public class MyRestResource {

    public MyRestResource() {
        log.info("ctor");
    }

    @GET
    @Path("/ping")
    @Produces(TEXT_PLAIN)
    public String ping() {
        return "pong";
    }

}
