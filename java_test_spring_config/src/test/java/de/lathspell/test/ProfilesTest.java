package de.lathspell.test;

import static java.util.Arrays.asList;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.lathspell.test.model.Person;

/**
 * Configuration using profiles.
 * 
 * Beans from profiles.xml are always loaded, beans from either
 * profiles-dev.xml or profiles-prod.xml depending on the activated
 * profile names.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/profiles*.xml")
@ActiveProfiles({"dev", "james"})
public class ProfilesTest {

    @Autowired
    private ApplicationContext ctx;

    @Autowired
    @Qualifier("MrDefault")
    private Person mrDefault;

    @Autowired
    @Qualifier("MrProfile")
    private Person mrProfile;

    @Test
    public void testProfiles() {
        assertThat(asList(ctx.getEnvironment().getActiveProfiles()), hasItems("dev", "james"));
        assertThat(mrDefault.getFullName(), is("Default Tester"));
        assertThat(mrProfile.getFullName(), is("Devel Tester"));
    }

}
