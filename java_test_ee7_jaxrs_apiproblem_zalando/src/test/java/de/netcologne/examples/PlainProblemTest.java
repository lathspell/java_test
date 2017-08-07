package de.netcologne.examples;

import java.net.URI;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

import org.junit.Test;
import org.zalando.problem.Problem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class PlainProblemTest {

    @Test
    public void testProblem() {
        Problem p = Problem.valueOf(NOT_FOUND);

        assertEquals(NOT_FOUND, p.getStatus());
        assertEquals("Not Found", p.getTitle());
        assertFalse(p.getDetail().isPresent());
    }

    @Test
    public void testProblem2() throws Exception {
        Problem p = Problem.valueOf(NOT_FOUND, "No such thing here!", new URI("http://api.netcologne.de/no-such-thing-found"));

        assertEquals(NOT_FOUND, p.getStatus());
        assertEquals("Not Found", p.getTitle());
        assertEquals("No such thing here!", p.getDetail().get());
        assertEquals("no-such-thing-found", p.getType().getPath());
    }
}
