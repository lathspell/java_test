package de.lathspell.test;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasToString;
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
public class TemplateTest {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testUsingItsTemplate() {
        Map<String, Object> result = jdbcTemplate.queryForMap("SELECT * FROM kv WHERE k = ?", "Tim");
        assertThat(result, hasToString("{K=Tim, V=Tayler}"));
    }
}
