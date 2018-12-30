package de.lathspell.test.springboot.web.rest;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/** This is a Spring MVC controller. */
@RestController
@RequestMapping("/rest/mvc")
public class GreetingController {

    @RequestMapping("/")
    public String home() {
        return "Hello World!";
    }

}
