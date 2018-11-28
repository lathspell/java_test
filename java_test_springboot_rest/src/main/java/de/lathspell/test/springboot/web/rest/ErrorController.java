package de.lathspell.test.springboot.web.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/rest/errors")
public class ErrorController {

    /**
     * Warning: when using this annotation on an exception class, or when setting the reason
     * attribute of this annotation, the HttpServletResponse.sendError method will be used.
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private static class My500Exception extends RuntimeException {
        public My500Exception(String message) {
            super(message);
        }
    }

    @GetMapping("/exception")
    public String throwException() {
        log.info("Throwing Exception!");
        throw new RuntimeException("Beware, a runtime Exception!");
    }


    @GetMapping("/exception-with-response-status-500")
    public String throwExceptionWithResponseStatus500() {
        log.info("Throwing a custom exception with @ResponseStatus(500)");
        throw new My500Exception("This is My500Exception!");
    }

    @GetMapping("/500")
    public ResponseEntity<String> get500() {
        log.info("Returning status 500");
        return new ResponseEntity<String>("Big body.", HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
