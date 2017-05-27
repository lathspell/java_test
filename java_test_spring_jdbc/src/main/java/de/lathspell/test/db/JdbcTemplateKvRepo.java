package de.lathspell.test.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("kvTemplateRepo")
public class JdbcTemplateKvRepo implements KvRepo {

    protected JdbcTemplate jdbcTemplate;

    private final RowMapper<Kv> rowMapper = new KvRowMapper();

    @Autowired
    public JdbcTemplateKvRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Kv findByKey(String key) {
        return jdbcTemplate.queryForObject("SELECT k, v FROM kv WHERE k = ?", rowMapper, key);
    }

    private class KvRowMapper implements RowMapper<Kv> {

        @Override
        public Kv mapRow(ResultSet rs, int rowNum) throws SQLException {
            Kv kv = new Kv();
            kv.setKey(rs.getString("k"));
            kv.setValue(rs.getString("v"));
            return kv;
        }
    }
}
