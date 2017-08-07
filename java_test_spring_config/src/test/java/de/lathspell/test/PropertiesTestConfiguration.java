package de.lathspell.test;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

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

    @Getter
    @Value("#{'${colorlist}'.split(',')}") // SpEL wrapping around Property to convert JSON-notation to Java List
    private List<String> colorList;

    @Getter
    @Value("#{${colormap}}") // SpEL wrapping around Property access to convert JSON-notation to Java Map
    private Map<Integer, String> colorMap;

    @Bean(name = "MrMu")
    public Person mrMuFactory() {
        return new Person(firstName, lastName, null);
    }

    /** Activate conversion of "1,2,3" to list or similar. */
    @Bean
    public ConversionService conversionService() {
        return new DefaultConversionService();
    }

    /**
     * This is kind of ugly.
     * Alternative to <context:property-placeholder/>
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
