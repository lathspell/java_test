package de.lathspell.test;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class RestTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void add() {
        assertThat(restTemplate.withBasicAuth("user", "user").getForObject("/rest/calc/add?a={a}&b={b}", String.class, 3, 4), containsString("Sign in"));
        assertEquals("12", restTemplate.withBasicAuth("machine", "machine").getForObject("/rest/calc/add?a={a}&b={b}", String.class, 3, 4));
    }
}
