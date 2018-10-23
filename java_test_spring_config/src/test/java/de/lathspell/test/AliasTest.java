package de.lathspell.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.lathspell.test.model.Person;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/alias.xml")
public class AliasTest {

    @Autowired
    private ApplicationContext ctx;
    
    @Test
    public void testOld() {
        assertThat(ctx.getBean("alex", Person.class).getFullName(), is("Alex Foo"));
        assertThat(ctx.getBean("alias2", Person.class).getFullName(), is("Alex Foo"));
        assertThat(ctx.getBean("alias3", Person.class).getFullName(), is("Alex Foo"));
        assertThat(ctx.getBean("alias4", Person.class).getFullName(), is("Alex Foo"));
        assertThat(ctx.getBean("alias5", Person.class).getFullName(), is("Alex Foo"));
        assertThat(ctx.getBean("alias6", Person.class).getFullName(), is("Alex Foo"));
    }
}
