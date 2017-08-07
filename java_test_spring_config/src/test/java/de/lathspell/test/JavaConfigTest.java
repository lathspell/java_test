package de.lathspell.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.lathspell.test.model.InitializedPerson;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JavaConfigTestConfiguration.class})
public class JavaConfigTest {

    @Autowired
    private InitializedPerson alex;
    
    @Test
    public void testJavaConfig() {
        assertThat(alex.getFullName(), is("Alex FOO"));
    }
}
