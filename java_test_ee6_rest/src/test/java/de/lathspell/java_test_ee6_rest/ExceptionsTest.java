package de.lathspell.java_test_ee6_rest;

import java.util.Map;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.MediaType.*;
import static javax.ws.rs.core.Response.Status.*;

import org.junit.Test;
import org.codehaus.jackson.map.ObjectMapper;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.glassfish.jersey.test.grizzly.GrizzlyTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.junit.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.Assert.*;

import de.lathspell.test.webservices.MyRestConfig;
import de.lathspell.test.webservices.exceptions.ApiProblem;

import static de.lathspell.test.webservices.exceptions.ApiProblem.APPLICATION_API_PROBLEM_JSON;
import static de.lathspell.test.webservices.exceptions.ApiProblem.APPLICATION_API_PROBLEM_JSON_TYPE;
import static javax.ws.rs.core.Response.Status.Family.CLIENT_ERROR;
import static javax.ws.rs.core.Response.Status.Family.SERVER_ERROR;

/**
 * This test acts as both, a server and a client, thus both sides have to
 * be configured if they want to used certain helper classes like
 * MessageBodyReaders/-Writers. The configure() method is used for the
 * server and configureClient for the client side.
 */
public class ExceptionsTest extends JerseyTest {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionsTest.class.getName());

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

    /** Automatically caught exceptions are not very useful. */
    @Ignore // We now use ApiProblemExceptionMapper!
    @Test
    public void getExceptionUnmapped() throws Exception {
        Response r = target("/myrest/getExceptionMapped").request().get(Response.class);
        // header
        assertEquals(INTERNAL_SERVER_ERROR.getStatusCode(), r.getStatus());
        assertEquals("Wrong state!", r.getStatusInfo().getReasonPhrase());
        assertNull(r.getMediaType());
        assertEquals(0, r.getLength());
    }

    /** Automatically caught exceptions are not very useful. */
    @Ignore // We now use ApiProblemExceptionMapper!
    @Test
    public void getExceptionMapped() throws Exception {
        Response r = target("/myrest/getExceptionMapped").request().get(Response.class);
        assertEquals(INTERNAL_SERVER_ERROR.getStatusCode(), r.getStatus());
        assertEquals(APPLICATION_API_PROBLEM_JSON_TYPE, r.getMediaType());
        ApiProblem ap = r.readEntity(ApiProblem.class);
        assertEquals("urn:java.lang.IllegalStateException", ap.getProblemType());
        assertEquals("Wrong state!", ap.getTitle());
    }

    /** Caught exception according to the Nottingham RFC draft. */
    @Test
    public void testApiProblemJsonException() throws Exception {
        Response r = target("/myrest/throwApiProblemJson").request().get();
        assertEquals(400, r.getStatus());
        assertEquals(BAD_REQUEST.getStatusCode(), r.getStatusInfo().getStatusCode());
        assertEquals("Bad Request", r.getStatusInfo().getReasonPhrase());
        assertEquals(CLIENT_ERROR, r.getStatusInfo().getFamily());
        assertEquals(APPLICATION_API_PROBLEM_JSON_TYPE, r.getMediaType());
        assertNull(r.getLocation());
        String body = r.readEntity(String.class
        );
        // Body as String
        assertEquals(
                "{\"title\":\"This is invalid!\",\"httpStatus\":\"400\",\"problemType\":\"urn:java.lang.Exception\"}", body);
        // Body as Map
        Map<String, String> map = new ObjectMapper().readValue(body, Map.class);

        assertEquals(
                3, map.size());
        assertEquals(
                "urn:java.lang.Exception", map.get("problemType"));
        assertEquals(
                "This is invalid!", map.get("title"));
        assertEquals(
                "400", map.get("httpStatus"));
        // Body as Object
        ApiProblem found = new ObjectMapper().readValue(body, ApiProblem.class);

        assertEquals(
                "urn:java.lang.Exception", found.getProblemType());
        assertEquals(
                "This is invalid!", found.getTitle());
        assertEquals(Integer.valueOf(400), found.getHttpStatus());
        assertNull(found.getDetail());
        assertNull(found.getProblemInstance());
        assertEquals(
                0, found.getExtras().size());
    }

    /** Caught rethrown exception according to the Nottingham RFC draft. */
    @Test
    public void testApiProblemJsonException2() throws Exception {
        Response r = target("/myrest/throwApiProblemJson2").request(APPLICATION_JSON).get();
        assertEquals(500, r.getStatus());
        assertEquals(INTERNAL_SERVER_ERROR.getStatusCode(), r.getStatus());
        assertEquals("Internal Server Error", r.getStatusInfo().getReasonPhrase());
        assertEquals(SERVER_ERROR, r.getStatusInfo().getFamily());
        assertEquals(APPLICATION_API_PROBLEM_JSON_TYPE, r.getMediaType());
        assertNull(r.getLocation());
        String json = r.readEntity(String.class);
        Map<String, String> map = new ObjectMapper().readValue(json, Map.class);
        assertEquals(7, map.size());
        assertEquals("urn:java.lang.IllegalArgumentException", map.get("problemType"));
        assertEquals("This is wrong!", map.get("title"));
        assertEquals("500", map.get("httpStatus"));
        assertEquals("The transaction was already open.", map.get("detail"));
        assertEquals("Transaction #1234", map.get("problemInstance"));
        assertEquals("value1", map.get("key1"));
        assertEquals("value2", map.get("key2"));
    }

    /** Caught rethrown exception as ApiProblem according to the Nottingham RFC draft. */
    @Test
    public void testApiProblemJsonException2b() throws Exception {
        Response r = target("/myrest/throwApiProblemJson2").request(APPLICATION_JSON).get();
        assertEquals(INTERNAL_SERVER_ERROR.getStatusCode(), r.getStatus());
        assertEquals(APPLICATION_API_PROBLEM_JSON_TYPE, r.getMediaType());
        ApiProblem ap = r.readEntity(ApiProblem.class);
        assertEquals("urn:java.lang.IllegalArgumentException", ap.getProblemType());
        assertEquals("This is wrong!", ap.getTitle());
        assertEquals(Integer.valueOf(500), ap.getHttpStatus());
        assertEquals("The transaction was already open.", ap.getDetail());
        assertEquals("Transaction #1234", ap.getProblemInstance());
        assertEquals(2, ap.getExtras().size());
        assertEquals("value1", ap.getExtras().get("key1"));
        assertEquals("value2", ap.getExtras().get("key2"));
    }
}
