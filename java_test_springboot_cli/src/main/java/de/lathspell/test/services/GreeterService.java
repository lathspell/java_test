package de.lathspell.test.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GreeterService {

    public GreeterService() {
        log.debug("ctor");
    }

    public void greet(String name) {
        System.out.println("Hello " + name);
    }

}
