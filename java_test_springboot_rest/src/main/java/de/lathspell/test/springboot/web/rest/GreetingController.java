package de.lathspell.test.springboot.web.rest;

import de.lathspell.test.springboot.model.Greeting;
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

    @RequestMapping("/hello/{name}")
    public Greeting name(@PathVariable("name") String name) {
        return new Greeting(null, name);
    }

    @RequestMapping(value = "/hello2/{name}")
    public String nameWithSalutation(@PathVariable("name") String name, @RequestParam(value = "salutation", defaultValue = "Dr.") String salutation) {
        return new Greeting(salutation, name).toString();
    }
}
