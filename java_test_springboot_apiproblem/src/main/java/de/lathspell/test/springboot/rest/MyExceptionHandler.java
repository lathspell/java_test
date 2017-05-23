package de.lathspell.test.springboot.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Exception Handler for all REST Controller.
 *
 * - Uses the original Spring method to map various Exceptions to sensible HTTP status codes.
 * - Adds a custom body as Spring's handler does not write a body.
 */
@ControllerAdvice
@Slf4j
public class MyExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    protected ResponseEntity<Object> handleInvalidUserInput(RuntimeException e, WebRequest request) {
        log.info("handling " + e.getClass());
        String body = "It's all your fault!";
        return myHandleExceptionInternal(e, body, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleEverythingElse(Exception e, WebRequest request) {
        log.info("handling " + e.getClass());
        ResponseEntity<Object> origResponse = super.handleException(e, request);
        return myHandleExceptionInternal(e, origResponse.getBody(), new HttpHeaders(), origResponse.getStatusCode());
    }

    private ResponseEntity<Object> myHandleExceptionInternal(Exception e, Object body, HttpHeaders headers, HttpStatus status) {
        log.warn("handling " + e.getClass());
        Object myBody = "<< " + e + " >>\n"; // Just an example! Stacktrace would be nice, too.
        return new ResponseEntity<>(myBody, headers, status);
    }

}
