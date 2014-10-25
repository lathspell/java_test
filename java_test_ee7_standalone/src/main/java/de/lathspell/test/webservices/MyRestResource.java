package de.lathspell.test.webservices;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lathspell.test.model.Person;

@Path("/myresource")
public class MyRestResource {

    private static final Logger log = LoggerFactory.getLogger(MyRestResource.class);

    @Inject
    private Person p;

    @GET
    @Path("/exception")
    public String exception() throws Exception {
        throw new Exception("myexception");
    }

    @GET
    @Path("/ping")
    @Produces(TEXT_PLAIN)
    public String ping() {
        log.debug("ping called");
        return "pong";
    }

    @GET
    @Path("/cdi")
    @Produces(TEXT_PLAIN)
    public String cdi() {
        log.debug("cdi called");
        return p.getName();
    }

}
