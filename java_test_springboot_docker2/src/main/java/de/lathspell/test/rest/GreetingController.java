package de.lathspell.test.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @GetMapping("/rest/greeting")
    public String index() {
        return "Hello world!";
    }

}
