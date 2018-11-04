package de.lathspell.test;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Main class adjusted to be run on an external Tomcat.
 * It extends from a ServletInitializer to conform to Servlet 3.0 without web.xml.
 * The main method was no longer necessary.
 */
@SpringBootApplication
public class JavaTestSpringbootServletApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(JavaTestSpringbootServletApplication.class);
    }

    /*
    public static void main(String[] args) {
        SpringApplication.run(JavaTestSpringbootServletApplication.class, args);
    }
    */
}
