package de.lathspell.test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import de.lathspell.test.model.Person;

@Configuration
@PropertySource("classpath:properties4.properties")
public class PropertiesTestConfiguration {

    /** Accessing the property using a Property Expression. */
    @Value("${mu_first}")
    private String firstName;

    /** Accessing the property using SpEL and the environment bean. */
    @Value("#{environment['mu_last']}")
    private String lastName;

    @Bean(name = "MrMu")
    public Person mrMuFactory() {
        return new Person(firstName, lastName, null);
    }

    /**
     * This is kind of ugly.
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer wtf() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
