package de.lathspell.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import de.lathspell.test.model.Person;

/**
 * A Java Configuration class loads an additional XML file using @ImportResource.
 */
public class ImportResourceTest {

    @Test
    public void test() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ImportResourceTestConfiguration.class);

        Person p = ctx.getBean("MrX", Person.class);
        assertThat(p.getFullName(), is("Xavier X"));

        Person p2 = ctx.getBean("MrY", Person.class);
        assertThat(p2.getFullName(), is("Yann Y"));

    }

}
