package de.lathspell.test.cdi.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import de.lathspell.test.cdi.spring.greeter.AbstractGreeter;
import de.lathspell.test.cdi.spring.greeter.EnglishGreeter;

@Profile("english")
@Configuration
public class AppConfigEnglish {

    @Bean
    public AbstractGreeter getGreeter() {
        AbstractGreeter g = new EnglishGreeter();
        g.setName("World");
        return g;
    }
}
