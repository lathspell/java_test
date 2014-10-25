package de.lathspell.java_test_ee6_stack1.frontend.rest;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Integration test!
 *
 * This test is not run by the maven-surefire-plugin during "mvn test" but
 * instead by the maven-failsafe-plugin after the .war was deployed to the
 * container.
 */
public class PostDeployIT {

    private static final String URL = "http://localhost:8080";

    private static final String CONTEXT_PATH = "/java_test_ee6_stack1";

    private static final String REST_PATH = "/rest";

    private static WebTarget target;

    @BeforeClass
    public static void beforeClass() {
        target = ClientBuilder.newClient().target(URL).path(CONTEXT_PATH).path(REST_PATH);
    }

    @Test
    public void getClientResponse() throws Exception {
        assertEquals("Hello Tim", target.path("/MyRestApi/hello/Tim").request(MediaType.APPLICATION_JSON).get(String.class));
    }
}
