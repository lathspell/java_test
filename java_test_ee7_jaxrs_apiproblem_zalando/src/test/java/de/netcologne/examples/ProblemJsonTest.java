package de.netcologne.examples;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.zalando.problem.Problem;

import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class ProblemJsonTest {

    @Test
    public void testJson() throws JsonProcessingException {
        Problem p = Problem.valueOf(BAD_REQUEST, "It's invalid!");

        String json = new ObjectMapper().enable(INDENT_OUTPUT).writeValueAsString(p);
        assertThat(json, containsString("\"type\" : \"about:blank\""));
        assertThat(json, containsString("\"message\" : \"Bad Request: It's invalid!\""));
        assertThat(json, containsString("\"status\" : \"BAD_REQUEST\""));
    }

}
