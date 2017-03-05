package de.lathspell.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import de.lathspell.test.model.Person;

public class PropertiesTest {

    @Test
    public void testBean() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("properties1.xml");
        Person p = ctx.getBean("MrPu", Person.class);
        assertThat(p.getFullName(), is("Peter Pu"));
    }

    @Test
    public void testContextNS() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("properties2.xml");
        Person p = ctx.getBean("MrKu", Person.class);
        assertThat(p.getFullName(), is("Karl Ku"));
    }

    @Test
    public void testUtilsNS() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("properties3.xml");
        Person p = ctx.getBean("MrFu", Person.class);
        assertThat(p.getFullName(), is("Frank Fu"));
    }
}
