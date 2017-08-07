package de.lathspell.test.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BazService {

    public String hello(String firstName, String lastName) {
        log.info("hello({}, {})", firstName, lastName);
        String greeting = "Hello " + firstName + " " + lastName;
        log.info("returning: " + greeting);
        return greeting;
    }
}
