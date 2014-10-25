package de.lathspell.java_test_ee6.ejb;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.ws.rs.core.MediaType;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public class MyEjbBeanTest {

    private static EJBContainer ejbContainer;
    private static Context ejbContext;
    private static MyEjbBean instance;

    public MyEjbBeanTest() {
    }

    @BeforeClass
    public static void setUpClass() throws NamingException {
        ejbContainer = EJBContainer.createEJBContainer();
        ejbContext = ejbContainer.getContext();
        instance = (MyEjbBean) ejbContext.lookup("java:global/classes/MyEjbBean");
    }

    @AfterClass
    public static void tearDownClass() {
        ejbContainer.close();

    }

    @Test
    public void testAddNumbers() throws Exception {
        assertEquals(3, instance.addNumbers(1, 2));
    }

    @Test
    @Ignore
    public void testAddNumbersHttp() throws Exception {
        WebResource service = Client.create().resource("http://localhost:8080/java_test_ee6/rest/test/");
        assertEquals("Hello Christian", service.path("/hello/Christian").accept(MediaType.TEXT_PLAIN).get(String.class));
    }
}