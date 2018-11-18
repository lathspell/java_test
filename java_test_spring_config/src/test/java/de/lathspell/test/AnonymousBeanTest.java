package de.lathspell.test;

import de.lathspell.test.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;

/**
 * Anonymous beans from XML configuration.
 * <p>
 * XML defined beans with no explicit name or id get an auto-generated id.
 * Beans declared with @Bean (without the name attribute) get the method name as id.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AnonymousBeanTest.AnonymousConfig.class)
@Slf4j
public class AnonymousBeanTest {

    @Configuration
    @ImportResource("/anonymous.xml")
    static class AnonymousConfig {
        @Bean
        public Person personMaker() {
            return new Person("Tim", "Tonne", LocalDate.of(1966, 2, 4));
        }
    }

    @Autowired
    private ApplicationContext ctx;

    @Test
    public void test1() {
        String[] names = ctx.getBeanDefinitionNames();
        Arrays.stream(names).forEach(name -> log.info("Found bean: {}", name));

        // The XML defined anonymous bean
        assertTrue(Arrays.stream(names)
                .filter(it -> it.matches("de.lathspell.test.model.Person#\\d+"))
                .findFirst()
                .isPresent());

        // The anonymous Bean defined with @Bean
        assertTrue(Arrays.stream(names)
                .filter(it -> it.matches("personMaker"))
                .findFirst()
                .isPresent());
    }
}
