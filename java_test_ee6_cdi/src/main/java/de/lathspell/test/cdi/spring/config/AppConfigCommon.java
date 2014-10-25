package de.lathspell.test.cdi.spring.config;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import de.lathspell.test.cdi.spring.SpringExample;
import de.lathspell.test.cdi.spring.greeter.AbstractGreeter;

@Configuration
@ImportResource("classpath:spring-application-config.xml")
@PropertySource("classpath:spring.properties")
public class AppConfigCommon {

    @Inject
    public String punctuation;

    @Inject
    Environment env;

    @Inject
    AbstractGreeter greeter;

    @Bean
    public SpringExample getSpringExample() {
        SpringExample ex = new SpringExample();
        ex.setGreeter(greeter);
        ex.setSmiley(env.getProperty("my.smiley"));
        ex.setPunctuation(punctuation);
        return ex;
    }
}
