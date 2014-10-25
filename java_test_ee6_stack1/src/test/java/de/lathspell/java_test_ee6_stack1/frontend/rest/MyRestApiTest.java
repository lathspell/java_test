package de.lathspell.java_test_ee6_stack1.frontend.rest;

import java.io.InputStream;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import de.lathspell.stack1.frontend.rest.MyRestConfig;
import de.lathspell.stack1.frontend.rest.exceptions.ApiProblem;

import static de.lathspell.stack1.frontend.rest.exceptions.ApiProblem.APPLICATION_API_PROBLEM_JSON_TYPE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Testing REST webservices using the Jersey Test Framework.
 *
 * The framework itself uses an Grizzly web server instance so the response
 * is really encoded to JSON/XML and send over the network.
 * Other webservers can be configured, even "memory" for pure API calls.
 *
 * This test acts as both, a server and a client, thus both sides have to
 * be configured if they want to used certain helper classes like
 * MessageBodyReaders/-Writers. The configure() method is used for the
 * server and configureClient for the client side.
 *
 * +---------------------------------------------------------
 * | T 127.0.0.1:56329 -> 127.0.0.1:9998 [AP]
 * | GET /MyRestApi/hello/Tim HTTP/1.1.
 * | Accept: application/json.
 * | User-Agent: Jersey/2.4 (HttpUrlConnection 1.7.0_25).
 * | Host: localhost:9998.
 * | Connection: keep-alive.
 * | .
 * |
 * | T 127.0.0.1:9998 -> 127.0.0.1:56329 [AP]
 * | HTTP/1.1 200 OK.
 * | Content-Type: application/json.
 * | Date: Fri, 01 Nov 2013 22:42:35 GMT.
 * | Content-Length: 9.
 * | .
 * | Hello Tim
 * +---------------------------------------------------------
 */
public class MyRestApiTest extends JerseyTest {

    /**
     * Configure the REST server just like the application server would do it.
     */
    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new MyRestConfig();
    }

    /**
     * Configure the REST client with the same helper classes as the server.
     */
    @Override
    protected void configureClient(ClientConfig config) {
        for (Class c : new MyRestConfig().getClasses()) {
            config.register(c);
        }
    }

    @Test
    public void getClientResponse() throws Exception {
        // simple
        assertEquals("Hello Tim", target().path("/MyRestApi/hello/Tim").request(MediaType.APPLICATION_JSON).get(String.class));

        // sophisticated
        Response response = target().path("/MyRestApi/hello/Tim").request(MediaType.APPLICATION_JSON).get();
        assertEquals(200, response.getStatus());
        assertEquals("OK", response.getStatusInfo().getReasonPhrase());
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(Response.Status.OK.getReasonPhrase(), response.getStatusInfo().getReasonPhrase());
        assertEquals(Response.Status.Family.SUCCESSFUL, response.getStatusInfo().getFamily());
        assertEquals(APPLICATION_JSON_TYPE, response.getMediaType());
        assertNull(response.getLocation());
        assertEquals(9, response.getLength());
        assertEquals("Hello Tim", IOUtils.toString((InputStream) response.getEntity()));
    }

    /** Caught rethrown exception according to the Nottingham RFC draft. */
    @Test
    public void testApiProblemException() throws Exception {
        Response r = target("/MyRestApi/apiProblem").request(APPLICATION_JSON).get();
        assertEquals(400, r.getStatus());
        assertEquals(BAD_REQUEST.getStatusCode(), r.getStatus());
        assertEquals(APPLICATION_API_PROBLEM_JSON_TYPE, r.getMediaType());
        ApiProblem ap = r.readEntity(ApiProblem.class);
        assertEquals("urn:java.lang.IllegalArgumentException", ap.getProblemType());
        assertEquals("Give me something else!", ap.getTitle());
    }

    @Test
    public void testGenericException() throws Exception {
        Response r = target("/MyRestApi/exception").request(APPLICATION_JSON).get();
        assertEquals(500, r.getStatus());
        assertEquals("Internal Server Error", r.getStatusInfo().getReasonPhrase()); // constant, not actually received!
        assertEquals(INTERNAL_SERVER_ERROR.getStatusCode(), r.getStatus());
        assertEquals(APPLICATION_API_PROBLEM_JSON_TYPE, r.getMediaType());
        ApiProblem ap = r.readEntity(ApiProblem.class);
        assertEquals("urn:java.lang.NullPointerException", ap.getProblemType());
        assertEquals("Zeeerooo!", ap.getTitle());
    }
}
