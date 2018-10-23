package de.lathspell.test;

import de.lathspell.test.model.Person;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.assertEquals;

public class RequiredTest {

    public static class PersonWithRequirements extends Person {
        boolean isFoo;

        @Required // forces the programmer to use this setter in XML bean creation
        public void setIsFoo(boolean isFoo) {
            this.isFoo = isFoo;
        }
    }


    @Test
    public void testGood() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("required.xml");
        PersonWithRequirements foo = ctx.getBean("MrFoo", PersonWithRequirements.class);
        assertEquals(true, foo.isFoo);
    }
}
