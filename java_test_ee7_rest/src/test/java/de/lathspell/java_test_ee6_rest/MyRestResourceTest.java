package de.lathspell.java_test_ee6_rest;

import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED_TYPE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static javax.ws.rs.core.MediaType.TEXT_HTML;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.Status.Family.SUCCESSFUL;
import static javax.ws.rs.core.Response.Status.OK;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

import de.lathspell.test.webservices.MyRestConfig;
import de.lathspell.test.webservices.MyRestResource.FullName;

/**
 * Tests for the REST resource class.
 *
 * See http://jersey.java.net/nonav/documentation/latest/test-framework.html
 * See faster variant on https://github.com/mlex/jerseytest/blob/master/mjl-jersey-server/src/test/java/de/codecentric/mjl/jerseytest/helpers/FastJerseyTest.java
 *
 * This test acts as both, a server and a client, thus both sides have to
 * be configured if they want to used certain helper classes like
 * MessageBodyReaders/-Writers. The configure() method is used for the
 * server and configureClient for the client side.
 */
public class MyRestResourceTest extends JerseyTest {

    @Override
    public Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new MyRestConfig();
    }

    @Override
    public void configureClient(ClientConfig config) {
        for (Class c : new MyRestConfig().getClasses()) {
            config.register(c);
        }
    }

    @Test
    public void getResponse() throws Exception {
        Response r = target("/myrest/getInteger").request().get();
        assertEquals(200, r.getStatus());
        assertEquals("OK", r.getStatusInfo().getReasonPhrase());
        assertEquals(OK.getStatusCode(), r.getStatus());
        assertEquals(SUCCESSFUL, r.getStatusInfo().getFamily());
        assertEquals(APPLICATION_JSON_TYPE, r.getMediaType());
        assertEquals(2, r.getLength());
        assertNull(r.getLocation());
        assertEquals("42", r.readEntity(String.class));
    }

    @Test
    public void testJacksonBoolean() throws IOException {
        assertEquals("true", new ObjectMapper().writeValueAsString(Boolean.TRUE));
    }

    /**
     * Those return all invalid JSON messages!
     *
     * (A JSON message must be an array or an object, not just a primitive!)
     *
     * @see MyRestResource.getInteger() for an explanation.
     */
    @Test
    public void testGetPrimitives() {
        assertEquals("42", target("/myrest/getInteger").request().get(String.class));
        assertEquals("42", target("/myrest/getInt").request().get(String.class));
        assertEquals("3.14", target("/myrest/getDouble").request().get(String.class));
        assertEquals("true", target("/myrest/getBoolean").request().get(String.class));
        assertEquals("true", target("/myrest/postBoolean").request().post(Entity.text("true"), String.class));
    }

    @Test
    public void testListsAndArrays() throws Exception {
        assertEquals("[\"a\",\"b\",\"c\"]", target("/myrest/getArray").request().get(String.class));
        assertEquals("[\"a\",\"b\",\"c\"]", target("/myrest/getList").request().get(String.class));
    }

    @Test
    public void testDate() throws Exception {
        Date d = new GregorianCalendar(2013, 5 - 1, 8, 0, 31, 42).getTime();
        assertEquals("" + d.getTime(), target("/myrest/getDate").request().get(String.class));
    }

    @Test
    public void testWadl() throws Exception {
        String wadl = target("/application.wadl").request().get(String.class);
        assertNotNull(wadl);
        assertTrue(wadl.contains("<resource path=\"/myrest\">"));
        System.out.println(wadl);
    }

    @Test
    public void testHello() {
        assertEquals("Hello <em>Tom</em>!", target("/myrest/hello/Tom").request().accept(TEXT_HTML).get(String.class));
    }

    @Test(expected = Exception.class)
    public void testHelloInvalidEmptyParam() {
        target("/myrest/hello").request().get(String.class);
    }

    @Test(expected = Exception.class)
    public void testHelloInvalidChars() {
        target("/myrest/hello/_foo").request().get(String.class);
    }

    @Test
    public void testGetFullName() {
        String xmlResponse = target("/myrest/getFullName").request(APPLICATION_XML).get(String.class);
        String expected
                = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<FullName>"
                + "<firstName>John</firstName>"
                + "<lastName>Doe</lastName>"
                + "<age>42</age>"
                + "</FullName>";
        assertEquals(expected, xmlResponse);

        FullName xmlObj = target("/myrest/getFullName").request(APPLICATION_XML).get(FullName.class);
        assertEquals("John", xmlObj.firstName);

        String jsonResponse = target("/myrest/getFullName").request(APPLICATION_JSON).get(String.class);
        assertEquals("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"other\":null}", jsonResponse);

        // FIXME
//        String jsonpResponse = target("/myrest/getFullNameWithPadding").request(APPLICATION_JSON).get(String.class);
//        assertEquals("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"other\":null}", jsonpResponse);
        // FIXME     FullName jsonObj = target("/myrest/getFullName").request(APPLICATION_JSON).get(FullName.class);
        // assertEquals("John", jsonObj.firstName);
    }

    @Test
    public void testCheckName() throws Exception {
        assertEquals("true", target("/myrest/checkName").request().post(Entity.entity("name=John", APPLICATION_FORM_URLENCODED_TYPE), String.class));
    }

    /**
     * I give up on this one...
     *
     * com.sun.jersey.api.client.Response getEntity:
     * "A message body reader for Java class java.lang.Boolean, and Java type class java.lang.Boolean, and MIME media type application/json was not found"
     */
    @Test(expected = Exception.class)
    public void testCheckNameException() {
        assertEquals("true", target("/myrest/checkName").request().post(Entity.json("name=John"), String.class));
        
        fail("Huh?");
    }
}
