package de.lathspell.test.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingResource {

    @GetMapping("/rest/hello")
    public String hello() {
        return "Hello!";
    }
}
