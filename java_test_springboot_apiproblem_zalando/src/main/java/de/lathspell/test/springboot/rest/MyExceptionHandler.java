package de.lathspell.test.springboot.rest;

import java.net.URI;

import javax.ws.rs.core.Response;

import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.spring.web.advice.ProblemHandling;

import de.lathspell.test.springboot.exceptions.OutOfTeaException;

/**
 * Enable the Zalando Exception handler that returns application/problem+json for all Exceptions.
 *
 * Remember to enable "throw-exception-if-no-handler-found" in application.yml to enable this
 * handler for invalid URLs as well.
 */
@ControllerAdvice
public class MyExceptionHandler implements ProblemHandling {

    @Override
    public boolean isCausalChainsEnabled() {
        return false;
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleOutOfTeaException(IllegalArgumentException e, NativeWebRequest req) {
        return create(Response.Status.SERVICE_UNAVAILABLE, e, req);
    }

    @ExceptionHandler
    @SneakyThrows
    public ResponseEntity<Problem> handleOutOfTeaException(OutOfTeaException e, NativeWebRequest req) {
        Problem problem = Problem.builder()
                .withStatus(Response.Status.NOT_ACCEPTABLE)
                .withTitle(e.getMessage())
                .withDetail(e.getMyDetails())
                .withType(new URI("exception:" + e.getClass().getCanonicalName()))
                .build();
        return create(e, problem, req);
    }
}
