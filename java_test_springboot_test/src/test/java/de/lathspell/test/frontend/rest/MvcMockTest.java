package de.lathspell.test.frontend.rest;

import de.lathspell.test.frontend.rest.frontend.rest.MyRestController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MyRestController.class)
public class MvcMockTest {

    @Autowired
    private MockMvc client;

    @Test
    public void test1() throws Exception {
        client.perform(get("/test1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("test1")));
    }

    @Test
    public void testBadRequest() throws Exception {
        client.perform(get("/bad-request"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Stupid client!")));
    }

    @Test(expected = NestedServletException.class)
    public void testException() throws Exception {
        client.perform(get("/exception"));
        /* does not work in MockMvc:
                .andExpect(status().is5xxServerError())
                .andExpect(content().string(containsString("Bad things")));
                */
    }
}
