package de.lathspell.test.springboot.web.rest;

import de.lathspell.test.springboot.model.Greeting;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** This is a Spring MVC controller. */
@RestController
@EnableAutoConfiguration
@RequestMapping("/rest/mvc/greeter")
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
