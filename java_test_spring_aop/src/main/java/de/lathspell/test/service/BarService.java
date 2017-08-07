package de.lathspell.test.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BarService {

    public String hello(String firstName) {
        log.info("hello({})", firstName);
        if (firstName == null) {
            throw new IllegalArgumentException("No name!");
        }
        return "Hello " + firstName;
    }
}
