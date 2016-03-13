package de.lathspell.test.runner;

import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import de.lathspell.test.services.GreeterService;

@Component
@Slf4j
public class AppRunner implements ApplicationRunner {

    @Autowired
    private GreeterService greeter;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("run with args: " + Arrays.deepToString(args.getSourceArgs()));
        if (args.containsOption("hello")) {
            args.getOptionValues("hello").stream().forEach((v) -> {
                greeter.greet(v);
            });
        } else {
            System.out.println("Please provide --hello=<NAME> !");
        }
    }

}
