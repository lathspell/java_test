package de.lathspell.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;

@SpringBootApplication
@Slf4j
public class Cli {

    public static void main(String[] args) throws Exception {
        log.info("entering main"); // Logback has "debug" disabled by default
        SpringApplication app = new SpringApplication(Cli.class);
        app.setWebEnvironment(false);
        log.info("web environment deactivated, now starting run()");
        app.run(args);
        log.debug("leaving main");
    }
}
