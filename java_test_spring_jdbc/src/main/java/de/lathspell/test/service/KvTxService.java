package de.lathspell.test.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Example of a database service that uses Transactions.
 */
@Service
@Slf4j
public class KvTxService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public void createKv(String k, String v) {
        jdbcTemplate.update("INSERT INTO kv (k, v) VALUES (?, ?)", k, v);
    }

    @Transactional
    public String findVByK(String k) {
        try {
            return jdbcTemplate.queryForObject("SELECT v FROM kv WHERE k = ?", String.class, k);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Transactional
    public void deleteByK(String k) {
        jdbcTemplate.update("DELETE FROM kv WHERE k = ?", k);
    }
}
