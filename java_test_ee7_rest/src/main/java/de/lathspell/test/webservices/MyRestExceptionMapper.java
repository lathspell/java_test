package de.lathspell.test.webservices;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

import com.fasterxml.jackson.core.JsonParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lathspell.test.webservices.exceptions.ApiProblemException;

@Provider
public class MyRestExceptionMapper implements ExceptionMapper<Exception> {

    public static final Logger log = LoggerFactory.getLogger(MyRestExceptionMapper.class);

    @Override
    public Response toResponse(Exception e) {
        if (e instanceof WebApplicationException) {
            StatusType st = ((WebApplicationException) e).getResponse().getStatusInfo();
            log.info("Cought WebApplicationException in REST request: {} {}", st.getStatusCode(), st.getReasonPhrase());
            log.debug("Stack trace:", e);
            return new ApiProblemException((WebApplicationException) e).getResponse();
        } else if (e instanceof JsonParseException) {
            // Problems while decoding incoming requests.
            log.warn("Cought JsonParseException in REST request: " + e.getMessage(), e);
            return new ApiProblemException(BAD_REQUEST, e).getResponse();
        } else {
            log.error("Cought Exception in REST request: " + e.getMessage(), e);
            return new ApiProblemException(INTERNAL_SERVER_ERROR, e).getResponse();
        }
    }
}
