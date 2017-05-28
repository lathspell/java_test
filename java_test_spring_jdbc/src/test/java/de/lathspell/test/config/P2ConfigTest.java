package de.lathspell.test.config;

import lombok.extern.slf4j.Slf4j;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import de.lathspell.test.config.AppConfiguration;

@RunWith(SpringRunner.class)
@ActiveProfiles("p2")
@ContextConfiguration(classes = AppConfiguration.class)
@Slf4j
public class P2ConfigTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void test1() {
        String result = jdbcTemplate.queryForObject("SELECT v FROM kv WHERE k = ?", String.class, "Tim");
        assertThat(result, is("Tayler"));
    }
}
