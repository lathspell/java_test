package de.lathspell.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import de.lathspell.test.model.Person;

@Configuration
@ImportResource("classpath:import-resource.xml")
public class ImportResourceTestConfiguration {

    @Bean("MrY")
    public Person mrYFactory() {
        return new Person("Yann", "Y", null);
    }
    
}
