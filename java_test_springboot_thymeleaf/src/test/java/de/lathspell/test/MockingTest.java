package de.lathspell.test;

import de.lathspell.test.web.MyWebController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = MyWebController.class)
public class MockingTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test1() throws Exception {
        mockMvc.perform(get("/test1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("test1"))
                .andExpect(content().string(containsString("<p>test1</p>")));
    }
}
