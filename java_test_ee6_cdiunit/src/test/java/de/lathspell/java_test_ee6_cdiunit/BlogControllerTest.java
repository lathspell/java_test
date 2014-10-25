package de.lathspell.java_test_ee6_cdiunit;

import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lathspell.java_test_ee6_cdiunit.model.BlogArticle;
import static org.junit.Assert.*;

@RunWith(CdiRunner.class)
@AdditionalClasses(BlogArticle.class)
public class BlogControllerTest {

    private static final Logger log = LoggerFactory.getLogger(BlogControllerTest.class);

    @Inject
    BlogController controller;

    @Test
    public void testGetHeader() {
        assertNotNull(controller.formatter);
        log.info("JUNIT testGetHeader: " + controller);
        controller.setText("This is a quite long text!");
        assertEquals("This is a  ...", controller.getShortExcerpt());
    }

}
