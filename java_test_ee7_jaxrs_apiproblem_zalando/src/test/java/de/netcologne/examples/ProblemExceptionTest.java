package de.netcologne.examples;

import java.net.URI;

import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

import org.junit.Test;
import org.zalando.problem.ThrowableProblem;

import static org.junit.Assert.assertEquals;

public class ProblemExceptionTest {

    public final class OutOfStockProblem extends ThrowableProblem {

        private String title;

        public OutOfStockProblem(String title) {
            this.title = title;
        }

        @Override
        public URI getType() {
            return URI.create("http://example.com/out-of-stock");
        }

        @Override
        public String getTitle() {
            return title;
        }

        @Override
        public Response.StatusType getStatus() {
            return BAD_REQUEST;
        }

    }

    @Test
    public void testThrowing() {
        ThrowableProblem e = new OutOfStockProblem("Nothing left!");
        assertEquals("Nothing left!", e.getTitle());
        assertEquals(BAD_REQUEST, e.getStatus());
    }
}
