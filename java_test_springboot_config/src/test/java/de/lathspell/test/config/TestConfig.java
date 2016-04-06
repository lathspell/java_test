package de.lathspell.test.config;

import de.lathspell.test.services.MyCtor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

import de.lathspell.test.Application;
import de.lathspell.test.config.FooConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class TestConfig {

    @Value("${info.app.name}")
    private String appName;

    @Autowired
    private MyCtor myCtor;

    @Autowired
    private FooConfig fooConfig;

    @Test
    public void smokeTest() {
        assertEquals("Spring Boot Test Application", appName);
    }

    @Test
    public void testBeanWithCtorInjection() {
        assertEquals("foo", myCtor.getName());
    }

    @Test
    public void testConfigurationBean() {
        assertEquals("admin", fooConfig.getUsername());
        assertArrayEquals(new byte[]{1, 2, 3, 4}, fooConfig.getRemoteAddress().getAddress());
    }
}
