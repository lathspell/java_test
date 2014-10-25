package de.lathspell.java_test_ee6_rest;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.junit.Test;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.junit.Assert.assertEquals;

/**
 * The ClientBuilder API is part of JAX-RS 2.0 which is only part of Java EE7!
*/
public class Client {

    private static final String URL = "http://localhost:8080";

    private static final String CONTEXT_PATH = "/java_test_ee6_rest";

    private static final String REST_PATH = "/rest";

    @Test
    public void test() {
        WebTarget target = ClientBuilder.newClient().target(URL).path(CONTEXT_PATH).path(REST_PATH);
        String result = target.path("/myrest/getInt").request(APPLICATION_JSON).get(String.class);
        assertEquals("42", result);
    }
}
