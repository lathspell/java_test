package de.lathspell.test;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import de.lathspell.test.config.FooConfig;

/**
 * Spring Boot entry point.
 */
@SpringBootApplication
@ComponentScan(basePackageClasses = Application.class)
@EnableConfigurationProperties(FooConfig.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
