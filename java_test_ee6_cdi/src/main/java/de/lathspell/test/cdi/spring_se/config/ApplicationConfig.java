package de.lathspell.test.cdi.spring_se.config;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import de.lathspell.test.cdi.spring_se.App;

@Configuration
public class ApplicationConfig {

    @Inject
    Environment env;

    @Bean
    public App produceApp() {
        App app = new App();
        app.setUrl(env.getProperty("soap.url"));
        return app;
    }
}