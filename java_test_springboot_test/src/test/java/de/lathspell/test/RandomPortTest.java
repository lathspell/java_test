package de.lathspell.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RandomPortTest {

    @Autowired
    private TestRestTemplate tpl;

    @Test
    public void test1() {
        ResponseEntity<String> resp = tpl.getForEntity("/test1", String.class);
        assertTrue(resp.getStatusCode().is2xxSuccessful());
        assertEquals("This is test1!", resp.getBody());
    }

    @Test
    public void testBadRequest() {
        ResponseEntity<String> resp = tpl.getForEntity("/bad-request", String.class);
        assertTrue(resp.getStatusCode().is4xxClientError());
        assertThat(resp.getBody(), containsString("Stupid client!"));
    }

    @Test
    public void testException() {
        ResponseEntity<String> resp = tpl.getForEntity("/exception", String.class);
        assertTrue(resp.getStatusCode().is5xxServerError());
        assertThat(resp.getBody(), containsString("Bad things"));
    }
}
