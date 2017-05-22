package de.lathspell.test.rest;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class RestIT {

    private static final String URL = "http://localhost:8090/java_test_spring_rest";

    private final RestTemplate tpl = new RestTemplate();

    @Test
    public void testHello1() {
        assertEquals("Hello1 Tim", tpl.getForObject(URL + "/?name=Tim", String.class));
    }

    @Test
    public void testHello2() {
        assertEquals("Hello2 Tim", tpl.getForObject(URL + "/hello2?name=Tim", String.class));
    }

    @Test
    public void testHello3() {
        assertEquals("Hello3 Tim", tpl.getForObject(URL + "/hello3?name=Tim", String.class));
    }

    @Test
    public void testHelloX() {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("X-Override", "true");
        HttpEntity<String> requestEntity = new HttpEntity<>("", headers);
        assertEquals("HelloX Tim", tpl.exchange(URL + "/doesnotmatter?name=Tim", HttpMethod.GET, requestEntity, String.class).getBody());
    }

    @Test
    public void testUppercase() {
        assertEquals("TIM", tpl.postForObject(URL + "/uppercase", "Tim", String.class));
    }
}
