package de.lathspell.test;

import java.util.Properties;

import lombok.extern.slf4j.Slf4j;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.lathspell.test.model.Person;

/**
 * The @Value annotation can be used to inject values using SpEL.
 *
 * @see PropertiesTest
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ValueTest.class})
@Slf4j
public class ValueTest {

    @Bean(name = "tom")
    public Person produceName() {
        log.info("producing Tom");
        return new Person("Tom", "T", null);
    }

    @Value("foo")
    private String foo;

    @Value("#{systemProperties['user.timezone']}") // systemProperties is pre-defined
    private String timezone;

    /** An instance of type Person is created using the Bean producer method and then referenced to get one of its attributes using SpEL. */
    @Value("#{tom.firstName}")
    private String tomsFirstName;

    @Test
    public void test() {
        assertThat(foo, is("foo"));
        assertThat(timezone, is("Europe/Berlin"));
        assertThat(tomsFirstName, is("Tom"));
    }
}
