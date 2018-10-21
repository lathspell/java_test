package de.lathspell.test;

import de.lathspell.test.model.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/date-converter.xml")
public class DateConverterTest {

    /**
     * Referenced in the XML
     */
    public static class MyLocalDateConverter implements PropertyEditorRegistrar {
        @Override
        public void registerCustomEditors(PropertyEditorRegistry registry) {
            registry.registerCustomEditor(LocalDate.class, new MyLocalDateEditor());
        }
    }

    public static class MyLocalDateEditor extends PropertyEditorSupport {
        public void setAsText(String text) throws IllegalArgumentException {
            setValue(LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy+MM+dd")));
        }

        public String getAsText() {
            throw new IllegalArgumentException("not yet implemented");
        }
    }

    @Autowired
    private Person p;

    @Test
    public void testDate() {
        assertThat(p.getBirthday(), is(LocalDate.of(2018, 10, 21)));
    }
}
