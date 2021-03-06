package test5;

import com.google.common.collect.ImmutableMap;
import common.model.Kv;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import test5.config.DbConfig;

import javax.sql.DataSource;
import java.sql.Connection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DbConfig.class)
public class NamedParameterJdbcTemplateTest {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Test
    public void crud() {
        Kv actual = jdbcTemplate.queryForObject("SELECT k, v FROM kv WHERE k = :name", ImmutableMap.of("name", "Tim"), new BeanPropertyRowMapper<>(Kv.class));
        assertEquals(new Kv("Tim", "Tayler"), actual);
    }

    @Test
    public void testGettingDataSource() throws Exception {
        DataSource ds = jdbcTemplate.getJdbcTemplate().getDataSource();
        assertThat(ds, isA(DataSource.class));

        Connection c = ds.getConnection();
        assertThat(c, isA(Connection.class));

        assertThat(c.getNetworkTimeout(), is(0)); // no timeout by default?
    }
}
