package de.lathspell.test.repo;

import java.sql.ResultSet;
import java.sql.SQLException;

import common.model.Kv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("kvTemplateRepo")
@Slf4j
public class KvRepoImpl implements KvRepo {

    protected JdbcTemplate jdbcTemplate;

    private final RowMapper<Kv> rowMapper = new KvRowMapper();

    @Autowired
    public KvRepoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Kv findByKey(String key) {
        return jdbcTemplate.queryForObject("SELECT k, v FROM kv WHERE k = ?", rowMapper, key);
    }

    /**
     * Uses a Callback Handler to do something with the rows.
     *
     * The handler is stateful while processing the resultset.
     */
    @Override
    public void loggingFindByKey() {
        jdbcTemplate.query("SELECT k, v FROM kv", new LoggingRowCallbackHandler());
    }

    private class LoggingRowCallbackHandler implements RowCallbackHandler {

        private int counter = 0;

        @Override
        public void processRow(ResultSet rs) throws SQLException {
            counter++;
            log.info("RS {}: {}", counter, rs);
        }

    }

    private class KvRowMapper implements RowMapper<Kv> {

        @Override
        public Kv mapRow(ResultSet rs, int rowNum) throws SQLException {
            Kv kv = new Kv();
            kv.setK(rs.getString("k"));
            kv.setV(rs.getString("v"));
            return kv;
        }
    }
}
