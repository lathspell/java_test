package de.lathspell.test;


import static java.time.format.DateTimeFormatter.ISO_DATE;

import javax.annotation.Resource;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.lathspell.test.model.Person;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/fancy.xml")
public class FancyXmlTest {

    // Inject using Spring annotations; Qualifier as there are two different beans of type "Person" in the XML.
    @Autowired
    @Qualifier("MrTaler")
    private Person mrTaler;

    // Inject using Java Common Annotations (throws Exception if name is unknown!)
    @Resource(name = "MrTaler2")
    private Person mrTaler2;

    // Injecting LocalDate using a string and a factory method
    @Autowired
    @Qualifier("MrOld")
    private Person mrOld;
    
    @Test
    public void testFancy() {
        assertThat(mrTaler.getFullName(), is("Tim Taler"));
        assertThat(mrTaler2.getFullName(), is("Tim Taler"));
    }
    
    @Test
    public void testOld() {
        assertThat(mrOld.getFullName(), is("Old Man"));
        assertThat(mrOld.getBirthday().format(ISO_DATE), is("1980-04-10"));
    }
}
