package de.lathspell.java_test_ee6_cdiunit;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.ProducesAlternative;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lathspell.java_test_ee6_cdiunit.model.BlogArticle;
import de.lathspell.java_test_ee6_cdiunit.utils.BlogArticleFormatter;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(CdiRunner.class)
public class MockedBlogControllerTest {

    private static final Logger log = LoggerFactory.getLogger(MockedBlogControllerTest.class);

    @Inject
    BlogController controller;

    @Produces
    @ProducesAlternative
    @Mock
    BlogArticleFormatter mockedFormatter;

    @Test
    public void testMockedGetHeader() {
        log.info("JUNIT testMockedGetHeader: " + controller);
        when(mockedFormatter.getExcerpt(any(BlogArticle.class), anyInt())).thenReturn("Buh!");
        assertEquals("Buh!", controller.getShortExcerpt());
    }

}
