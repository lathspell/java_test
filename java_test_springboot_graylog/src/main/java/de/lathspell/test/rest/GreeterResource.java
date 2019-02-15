package de.lathspell.test.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class GreeterResource {

    @GetMapping("/hello")
    public String getHello() {
        return "Hello World!";
    }
    
}
