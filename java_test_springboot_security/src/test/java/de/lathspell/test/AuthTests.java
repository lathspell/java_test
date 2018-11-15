package de.lathspell.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class AuthTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void asAdmin() {
        TestRestTemplate tpl = restTemplate.withBasicAuth("admin", "admin");
        assertThat(tpl.getForObject("/admin/test1", String.class), containsString("admin test1"));
        assertThat(tpl.getForObject("/user/test1", String.class), containsString("user test1"));
        assertThat(tpl.getForObject("/user/test2", String.class), containsString("user test2"));
        assertThat(tpl.getForObject("/rest/calc/add?a=3&b=4", String.class), is("7"));
    }

    @Test
    public void asUser() {
        TestRestTemplate tpl = restTemplate.withBasicAuth("user", "user");
        assertThat(tpl.getForObject("/admin/test1", String.class), containsString("Forbidden"));
        assertThat(tpl.getForObject("/user/test1", String.class), containsString("user test1"));
        assertThat(tpl.getForObject("/user/test2", String.class), containsString("Forbidden"));
        assertThat(tpl.getForObject("/rest/calc/add?a=3&b=4", String.class), containsString("Forbidden"));

    }

    @Test
    public void asMachine() {
        TestRestTemplate tpl = restTemplate.withBasicAuth("machine", "machine");
        assertThat(tpl.getForObject("/admin/test1", String.class), containsString("Forbidden"));
        assertThat(tpl.getForObject("/user/test1", String.class), containsString("Forbidden"));
        assertThat(tpl.getForObject("/user/test2", String.class), containsString("Forbidden"));
        assertThat(tpl.getForObject("/rest/calc/add?a=3&b=4", String.class), is("7"));
    }
}
