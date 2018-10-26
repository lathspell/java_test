package test6;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import test6.config.DbConfig;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DbConfig.class)
@Slf4j
public class PlatformTransactionManagerTest {

    @Autowired
    private PlatformTransactionManager txManager;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void crud() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        def.setTimeout(300);

        TransactionStatus txStatus = txManager.getTransaction(def);

        try {
            jdbcTemplate.update("INSERT INTO kv (k, v) VALUES (?, ?)", "3.key", "3.value");
            jdbcTemplate.update("INSERT INTO broken_sql VALUES (1)", "");

            log.info("Committing");
            txManager.commit(txStatus);
        } catch (Exception e) {
            log.info("Rolling back due to: " + e.getMessage());
            txManager.rollback(txStatus);
        }

        assertEquals(new Long(2), jdbcTemplate.queryForObject("SELECT count(*) c FROM kv", Long.class));

    }
}
