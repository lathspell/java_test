package de.lathspell.test.webservices;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.glassfish.grizzly.utils.Exceptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class MyRestExceptionMapper implements ExceptionMapper<Exception> {

    public static final Logger log = LoggerFactory.getLogger(MyRestExceptionMapper.class);

    @Override
    public Response toResponse(Exception e) {
        log.warn("Caught Exception!", e);
        return Response.status(500).entity(Exceptions.getStackTraceAsString(e)).type("text/plain").build();
    }

}
