package de.lathspell.test;

import lombok.extern.slf4j.Slf4j;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.lathspell.test.model.Person;

/**
 * Using @Lazy to postpone creation of autowired objects.
 *
 * The annotation is necessary in the @Configuration class as @Bean would create an singleton instance as soon as the configuration is read.
 *
 * The annotation is also necessary in this class as the object would otherwise be produced the moment this class is instanciated and autowired.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {LazyTestConfiguration.class})
@Slf4j
public class LazyTest {

    @Lazy
    @Autowired
    private Person slow;

    @Test
    public void testJavaConfig() {
        log.info("LazyTest ist instanciated but 'slow' is only an empty proxy");
        // ... until now ...
        assertThat(slow.getFirstName(), is("Fat"));
    }
}
