package de.lathspell.test.cdi.spring;

import javax.inject.Inject;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import de.lathspell.test.cdi.MyArquillianTemplate;
import de.lathspell.test.cdi.spring.config.AppConfigEnglish;
import de.lathspell.test.cdi.spring.config.AppConfigGerman;
import de.lathspell.test.cdi.spring.config.AppConfigCommon;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfigCommon.class, AppConfigEnglish.class, AppConfigGerman.class}, loader = AnnotationConfigContextLoader.class)
@ActiveProfiles("german")
public class SpringExampleGermanTest extends MyArquillianTemplate {

    @Inject
    SpringExample springExample;

    @Test
    public void testSpringExampleApp() {
        assertNotNull(springExample);
        assertEquals("Localized greeter says: Hallo Welt! :-)", springExample.getGreeting());
    }
}