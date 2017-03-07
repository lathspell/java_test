package de.lathspell.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.lathspell.test.model.Person;

@Configuration
@Slf4j
class BeanFactoryPostProcessorTestConfiguration {

    /** Creating a Person object without setting the birthday. */
    @Bean("newborn")
    public Person newbornFactory() {
        log.info("Creating person");
        Person p = new Person("Lilly", "Lu", null);
        log.info("Created: " + p);
        return p;
    }
}
