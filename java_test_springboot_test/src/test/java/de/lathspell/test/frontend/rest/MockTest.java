package de.lathspell.test.frontend.rest;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class MockTest {

    @Autowired
    private MockMvc client;

    @Test
    public void test1() throws Exception {
        client.perform(get("/rest/test1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("test1")));
    }

    @Test
    public void testBadRequest() throws Exception {
        client.perform(get("/rest/bad-request"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Stupid client!")));
    }

    @Test(expected = NestedServletException.class)
    public void testException() throws Exception {
        client.perform(get("/rest/exception"));
        /* does not work in MockMvc:
                .andExpect(status().is5xxServerError())
                .andExpect(content().string(containsString("Bad things")));
                */
    }
}
