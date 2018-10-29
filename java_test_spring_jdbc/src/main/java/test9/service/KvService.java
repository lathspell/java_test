package test9.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
public class KvService {

    private JdbcTemplate jdbcTemplate;

    public KvService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public long countGood() {
        return jdbcTemplate.queryForObject("SELECT count(*) c FROM kv", Long.class);
    }

    @Transactional
    public long countBad() {
        return jdbcTemplate.queryForObject("SELECT bad grammer FROM kv", Long.class);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public long countFancy() {
        return jdbcTemplate.queryForObject("SELECT count(*) c FROM kv", Long.class);
    }

    @Transactional()
    public void badTransaction() {
        jdbcTemplate.update("INSERT INTO kv VALUES (?, ?)", "a", "b");
        jdbcTemplate.update("INSERT INTO kkv VALUES (?, ?)", "a", "b"); // bad SQL!
    }
}
