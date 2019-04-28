package de.lathspell.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.containers.wait.strategy.WaitAllStrategy;

import static org.junit.Assert.assertEquals;

@Slf4j
public class DockerAppIT {

    @ClassRule
    public static GenericContainer app = new GenericContainer("de.lathspell.test/java_test_springboot_docker2:latest")
            .withEnv("SPRING_PROFILES_ACTIVE", "it")
            .withExposedPorts(8080)
            .withFileSystemBind("/var/log/test", "/var/log/app", BindMode.READ_WRITE)
            .withLogConsumer(new Slf4jLogConsumer(log))
            .waitingFor(Wait.forHttp("/").forStatusCode(200));

    private TestRestTemplate tpl;

    @Before
    public void before() {
        tpl = new TestRestTemplate(new RestTemplateBuilder().rootUri("http://localhost:" + app.getFirstMappedPort()), null, null);
    }

    @Test
    public void testGreeting() {
        assertEquals("Hello world!", tpl.getForObject("/rest/greeting", String.class));
    }
}
