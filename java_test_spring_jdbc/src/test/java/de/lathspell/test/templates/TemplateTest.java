package de.lathspell.test.templates;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasToString;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import de.lathspell.test.config.AppConfiguration;

@RunWith(SpringRunner.class)
@ActiveProfiles("p3")
@ContextConfiguration(classes = AppConfiguration.class)
@Slf4j
public class TemplateTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Test
    public void testUsingItsTemplate() {
        Map<String, Object> result = jdbcTemplate.queryForMap("SELECT * FROM kv WHERE k = ?", "Tim");
        assertThat(result, hasToString("{K=Tim, V=Tayler}"));
    }

    @Test
    public void testNamedParameters() {
        Map<String, Object> params = new HashMap<>();
        params.put("keyName", "Tim");
        Map<String, Object> result = namedParameterJdbcTemplate.queryForMap("SELECT * FROM kv WHERE k = :keyName", params);
        assertThat(result, hasToString("{K=Tim, V=Tayler}"));
    }

}
