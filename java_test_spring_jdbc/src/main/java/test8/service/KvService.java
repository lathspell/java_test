package test8.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class KvService {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public KvService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public long countGood() {
        return jdbcTemplate.queryForObject("SELECT count(*) c FROM kv", Long.class);
    }

    @Transactional(readOnly = true)
    public long countBad() {
        return jdbcTemplate.queryForObject("SELECT bad grammer FROM kv", Long.class);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public long countFancy() {
        return jdbcTemplate.queryForObject("SELECT count(*) c FROM kv", Long.class);
    }
}
