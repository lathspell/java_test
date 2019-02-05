package de.lathspell.test.springboot.web.rest;

import org.springframework.web.bind.annotation.*;

@RestController
public class GreetingController {

    @RequestMapping("/rest/hello/{name}")
    public String name(@PathVariable("name") String name) {
        return "Hello " + name + "!";
    }

}
