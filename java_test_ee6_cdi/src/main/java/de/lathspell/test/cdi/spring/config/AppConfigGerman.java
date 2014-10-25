package de.lathspell.test.cdi.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import de.lathspell.test.cdi.spring.greeter.AbstractGreeter;
import de.lathspell.test.cdi.spring.greeter.GermanGreeter;

@Profile("german")
@Configuration
public class AppConfigGerman {

    @Bean
    public AbstractGreeter getGreeter() {
        AbstractGreeter g = new GermanGreeter();
        g.setName("Welt");
        return g;
    }
}
