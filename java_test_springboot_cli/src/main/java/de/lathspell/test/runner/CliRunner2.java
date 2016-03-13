package de.lathspell.test.runner;

import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.core.annotation.Order;

import de.lathspell.test.services.GreeterService;

@Order(20)
@Component
@Slf4j
public class CliRunner2 implements CommandLineRunner {

    @Autowired
    private GreeterService greeter;

    @Override
    public void run(String... args) throws Exception {
        log.info("run with args: " + Arrays.asList(args));
        if (args.length >= 1) {
            greeter.greet(args[0].replaceFirst("--hello=", ""));
        }
    }

}
