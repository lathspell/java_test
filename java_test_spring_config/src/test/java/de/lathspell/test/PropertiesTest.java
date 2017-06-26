package de.lathspell.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import de.lathspell.test.model.Person;

@Slf4j
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

    /** This one uses PropertiesTestConfiguration instead of an XML file. */
    @Test
    public void testJavaConfig() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertiesTestConfiguration.class);

        PropertiesTestConfiguration ptc = ctx.getBean(PropertiesTestConfiguration.class);
        Person p = ctx.getBean("MrMu", Person.class);

        // Object
        assertThat(p.getFullName(), is("Marlin Mu"));
        // Map
        assertThat(ptc.getColorMap().keySet().size(), is(3));
        assertThat(ptc.getColorMap().get(20), is("green"));
        // List
        assertThat(ptc.getColorList().size(), is(3));
        assertThat(ptc.getColorList().get(0), is("red"));

        // All properties read from Property Sources end up in the Spring Environment
        assertThat(ctx.getEnvironment().getProperty("mu_first"), is("Marlin"));
    }

}
