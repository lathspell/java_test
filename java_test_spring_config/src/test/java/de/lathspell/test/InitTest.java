package de.lathspell.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.lathspell.test.model.InitializedPerson;
import de.lathspell.test.model.InititializedPerson2;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/init.xml")
public class InitTest {

    @Autowired
    private InitializedPerson bert;

    @Autowired
    private InititializedPerson2 cloe;

    @Test
    public void testInit() {
        assertThat(bert.getFullName(), is("Bert BU"));
        assertThat(cloe.getFullName(), is("Cloe CU"));
    }
}
