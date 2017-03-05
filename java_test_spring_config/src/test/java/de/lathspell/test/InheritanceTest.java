package de.lathspell.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.lathspell.test.model.Person;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/inheritance.xml")
public class InheritanceTest {

    @Autowired
    private Person alex;

    @Autowired
    private Person bob;
    
    @Test
    public void testOld() {
        assertThat(alex.getFullName(), is("Alex Foo"));
        assertThat(bob.getFullName(), is("Bob Foo"));
    }
}
