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
@ContextConfiguration("/alias.xml")
public class AliasTest {

    @Autowired
    private Person alex;

    @Autowired
    private Person alex2;
    
    @Test
    public void testOld() {
        assertThat(alex.getFullName(), is("Alex Foo"));
        assertThat(alex2.getFullName(), is("Alex Foo"));
    }
}
