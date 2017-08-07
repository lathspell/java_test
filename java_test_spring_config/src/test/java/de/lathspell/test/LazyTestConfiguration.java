package de.lathspell.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import de.lathspell.test.model.Person;

@Configuration
@Slf4j
public class LazyTestConfiguration {

    @Lazy
    @Bean
    public Person slowPersonProducer() {
        log.info("Producing, takes a while...");
        return new Person("Fat", "Fatti", null);
    }
}
