package test4;

import com.google.common.collect.ImmutableMap;
import common.model.Kv;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import test4.config.DbConfig;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DbConfig.class)
public class SimpleJavaConfigTest {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Test
    public void crud() {
        RowMapper<Kv> rowMapper = new RowMapper<Kv>() {
            @Override
            public Kv mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Kv(rs.getString("k"), rs.getString("v"));
            }
        };

        Kv actual = jdbcTemplate.queryForObject("SELECT k, v FROM kv WHERE k = :name", ImmutableMap.of("name", "Tim"), rowMapper);
        assertEquals(new Kv("Tim", "Tayler"), actual);
    }
}
