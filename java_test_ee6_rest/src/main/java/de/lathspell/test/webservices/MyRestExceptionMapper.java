package de.lathspell.test.webservices;

import de.lathspell.test.webservices.exceptions.ApiProblemException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

@Provider
public class MyRestExceptionMapper implements ExceptionMapper<Exception> {

    public static final Logger log = LoggerFactory.getLogger(MyRestExceptionMapper.class);

    @Override
    public Response toResponse(Exception e) {
        if (e instanceof WebApplicationException) {
            log.info("{}", ((WebApplicationException) e).getResponse().getStatus(), e);
            return new ApiProblemException((WebApplicationException) e).getResponse();
        } else if (e instanceof org.codehaus.jackson.JsonParseException) {
            // Problems while decoding incoming requests.
            log.warn(e.getMessage(), e);
            return new ApiProblemException(BAD_REQUEST, e).getResponse();
        } else {
            log.error(e.getMessage(), e);
            return new ApiProblemException(INTERNAL_SERVER_ERROR, e).getResponse();
        }
    }
}
