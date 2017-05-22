package de.lathspell.test.config;


import lombok.extern.slf4j.Slf4j;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = NONE)
@Slf4j
public class TestFooConfig {

    @Autowired
    private FooConfig fooConfig;

    @Test
    public void testConfigurationBean() {
        assertEquals("admin", fooConfig.getUsername());
        assertArrayEquals(new byte[]{1, 2, 3, 4}, fooConfig.getRemoteAddress().getAddress());
        assertThat(fooConfig.getCountries(), contains("France", "Iceland"));
        assertThat(fooConfig.getPlayers().toString(), is("{20=Bob, 10=Alice, 30=Charlie}"));
    }
}
