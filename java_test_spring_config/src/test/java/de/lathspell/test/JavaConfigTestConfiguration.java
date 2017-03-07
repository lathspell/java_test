package de.lathspell.test;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import de.lathspell.test.model.InitializedPerson;

@Configuration
@ComponentScan(basePackages = "de.lathspell.test.justanexample") // <-- to find other @Bean classes
@Slf4j
public class JavaConfigTestConfiguration {

    public JavaConfigTestConfiguration() {
        log.info("ctor");
    }

    @Bean(name = "alex", initMethod = "initMethod")
    public InitializedPerson alexFactory() {
        log.info("Entering alexFactory");
        InitializedPerson p = new InitializedPerson();
        p.setFirstName("Alex");
        p.setLastName("Foo");
        return p;
    }

}
