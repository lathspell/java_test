package de.netcologne.examples.server.exceptions;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

import lombok.extern.slf4j.Slf4j;
import org.zalando.problem.Problem;
import org.zalando.problem.ThrowableProblem;

@Provider
@Slf4j
public class RestServerExceptionMapper implements ExceptionMapper<Exception> {

    public final static MediaType PROBLEM_JSON = MediaType.valueOf("application/problem+json");

    @Override
    public Response toResponse(Exception e) {
        final ThrowableProblem tp;
        if (e instanceof ThrowableProblem) {
            log.warn("Caught Problem in REST request: " + e.getMessage(), e);
            tp = ((ThrowableProblem) e);
        } else {
            log.warn("Caught Exception in REST request: " + e.getMessage(), e);
            tp = Problem.builder().withStatus(INTERNAL_SERVER_ERROR).withTitle(e.getMessage()).withDetail(e.toString()).build();
        }
        return Response.status(tp.getStatus()).type(PROBLEM_JSON).entity(tp).build();
    }
}
