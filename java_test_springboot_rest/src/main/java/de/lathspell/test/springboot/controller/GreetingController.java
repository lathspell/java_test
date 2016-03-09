package de.lathspell.test.springboot.controller;

import de.lathspell.test.springboot.model.Greeting;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@RequestMapping("/rest")
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
