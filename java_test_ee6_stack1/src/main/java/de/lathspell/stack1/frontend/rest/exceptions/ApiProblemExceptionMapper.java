package de.lathspell.stack1.frontend.rest.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

@Provider
public class ApiProblemExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {
        if (e instanceof WebApplicationException) {
            return new ApiProblemException((WebApplicationException) e).getResponse();
        } else if (e instanceof org.codehaus.jackson.JsonParseException) {
            // Problems while decoding incoming requests.
            return new ApiProblemException(BAD_REQUEST, e).getResponse();
        } else {
            return new ApiProblemException(INTERNAL_SERVER_ERROR, e).getResponse();
        }
    }
}
