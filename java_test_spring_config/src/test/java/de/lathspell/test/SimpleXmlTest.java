package de.lathspell.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import de.lathspell.test.model.Person;

public class SimpleXmlTest {

    @Test
    public void testOverride() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/simple1.xml", "/simple2.xml");
        
        Person pTaler = ctx.getBean("MrTaler", Person.class);
        assertThat(pTaler.getFullName(), is("Tim Taler"));
    }
}
