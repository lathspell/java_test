package de.lathspell.test.rest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * REST Web Service
 */
@Path("books")
public class BooksApi {

    private static final Logger log = LoggerFactory.getLogger(BooksApi.class);

    @Context
    private UriInfo context;

    @POST
    @Path("/{id}/title")
    @Produces("text/plain")
    public String getTitle(@PathParam("id") long id, String data) {
        log.info("Inside getTitle({}) with |{}|", id, data);
        return "Volume " + id + "/" + data;
    }

}
