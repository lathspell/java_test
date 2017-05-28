package de.lathspell.test.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Example of a database service that uses Transactions (programmatically).
 */
@Service
@Slf4j
public class ProgrammaticKvTxService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final TransactionTemplate txTpl;

    @Autowired
    public ProgrammaticKvTxService(PlatformTransactionManager txManager) {
        this.txTpl = new TransactionTemplate(txManager);
    }

    /**
     * Using txTemplate with an inline implementation of the abstract class.
     */
    public void createKv(String k, String v) {
        txTpl.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                jdbcTemplate.update("INSERT INTO kv (k, v) VALUES (?, ?)", k, v);
            }
        });
    }

    /**
     * Using Java 8 Lambda Expression.
     */
    public String findVByK(String k) {
        try {
            return txTpl.execute((TransactionStatus status) -> jdbcTemplate.queryForObject("SELECT v FROM kv WHERE k = ?", String.class, k));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * Using Java 8 Lambda Expression.
     */
    public void deleteByK(String k) {
        txTpl.execute((TransactionStatus status) -> jdbcTemplate.update("DELETE FROM kv WHERE k = ?", k));
    }
}
