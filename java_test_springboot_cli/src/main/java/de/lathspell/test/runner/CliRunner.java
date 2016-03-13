package de.lathspell.test.runner;

import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import de.lathspell.test.services.GreeterService;

@Order(10)
@Component
@Slf4j
public class CliRunner implements CommandLineRunner {

    @Autowired
    private GreeterService greeter;
    
    @Override
    public void run(String... args) throws Exception {
        log.info("run with args: " + Arrays.asList(args));

        for (String a : args) {
            if (a.startsWith("--hello=")) {
                greeter.greet(a.replaceFirst("^--hello=", ""));
            }
        }
    }

}
