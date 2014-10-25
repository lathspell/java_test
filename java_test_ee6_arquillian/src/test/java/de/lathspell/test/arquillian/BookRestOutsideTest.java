package de.lathspell.test.arquillian;

import java.net.URL;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.sun.jersey.api.client.Client;

import de.lathspell.test.model.Book;
import static org.junit.Assert.*;

import de.lathspell.test.rest.BookRest;

@RunWith(Arquillian.class)
public class BookRestOutsideTest {

    @ArquillianResource
    URL baseUrl;

    @Deployment(testable = false)
    public static WebArchive createTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage(Book.class.getPackage())
                .addPackage(BookRest.class.getPackage());
    }

    @Test
    @RunAsClient
    public void test() throws Exception {
        assertNotNull(baseUrl);
        String body = Client.create().resource(baseUrl.toString() + "rest/book/getAuthor").get(String.class);
        assertEquals("Jules Verne", body);
    }
}
