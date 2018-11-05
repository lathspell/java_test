package de.lathspell.test;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RandomPortTest {

    @Autowired
    private WebTestClient client;

    @Test
    public void test1() {
        client.get().uri("/test1").exchange()
                .expectStatus().isOk()
                .expectBody(String.class).value(Matchers.containsString("test1"));
    }

    @Test
    public void testBadRequest() {
        client.get().uri("/bad-request").exchange()
                .expectStatus().isBadRequest()
                .expectBody(String.class).value(Matchers.containsString("Stupid client!"));
    }

    @Test
    public void testException() {
        client.get().uri("/exception").exchange()
                .expectStatus().is5xxServerError()
                .expectBody(String.class).value(Matchers.containsString("Bad things"));
    }
}
