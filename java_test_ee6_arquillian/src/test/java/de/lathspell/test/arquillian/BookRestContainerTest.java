package de.lathspell.test.arquillian;

import java.io.File;
import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import de.lathspell.test.model.Book;
import de.lathspell.test.rest.BookRest;

/**
 * Run test inside container.
 *
 * This test is supposed to be included in the test.war and then executed via
 * the ArquillianTestServlet.
 *
 * Does not work though, the injected bookRest stays NULL.
 */
@RunWith(Arquillian.class)
public class BookRestContainerTest {

    @Inject
    BookRest bookRest;

    @Deployment // (testable = true)
    public static WebArchive createTestArchive() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackages(true, "de.lathspell.java_test_ee6_arquillian")
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/beans.xml"))
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/web.xml"));
        System.out.println(war.toString(true));
        return war;
    }

    @Test
    public void test() {
        assertNotNull(bookRest);
        assertEquals("Jules Verne", bookRest.getAuthor());
    }
}
