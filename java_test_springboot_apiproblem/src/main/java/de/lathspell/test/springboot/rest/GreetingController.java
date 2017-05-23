package de.lathspell.test.springboot.rest;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/greeting")
public class GreetingController {

    @GetMapping("/hello")
    public String name(@RequestParam(name = "name", required = false) String name) {
        Assert.notNull(name, "Name may not be NULL!"); // IllegalArgumentException
        return "Hello " + name;
    }

    @GetMapping("/crash")
    public void crash() {
        throw new RuntimeException("Kaboom!");
    }
}
