package de.lathspell.test;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import de.lathspell.test.helper.NewbornPersonBeanFactoryPostProcessor;
import de.lathspell.test.model.Person;

/**
 * Uses a BeanFactoryPostProcessor to modify an object at the time it is instantiated.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {BeanFactoryPostProcessorTestConfiguration.class, NewbornPersonBeanFactoryPostProcessor.class})
public class BeanFactoryPostProcessorTest {

    /** Created by BeanFactoryPostProcessorTestConfiguration.newbornFactory and modifed by NewbornPersonBeanFactoryPostProcessor. */
    @Autowired
    private Person newborn;

    @Test
    public void test() {
        assertThat(newborn.getFullName(), is("Lilly Lu"));
        assertNotNull("Birthday is still NULL", newborn.getBirthday());
        assertThat(newborn.getBirthday().getYear(), is(LocalDate.now().getYear()));
    }

}
