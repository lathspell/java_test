package de.lathspell.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;

@SpringBootApplication
@Slf4j
public class Application {

    public static void main(String[] args) {
        log.info("main");
        SpringApplication.run(Application.class, args);
    }

}
