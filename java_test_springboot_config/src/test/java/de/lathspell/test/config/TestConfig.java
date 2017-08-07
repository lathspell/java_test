package de.lathspell.test.config;

import lombok.extern.slf4j.Slf4j;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = NONE)
@Slf4j
public class TestConfig {

    @Value("${info.app.name}")
    private String appName;

    @Autowired
    private Environment env;

    @Test
    public void printEnv() {
        assertThat(env.getActiveProfiles().length, is(0));
        assertThat(env.toString(), containsString("classpath:/application.properties"));
    }

    @Test
    public void smokeTest() {
        assertEquals("Spring Boot Test Application", appName);
    }

}
