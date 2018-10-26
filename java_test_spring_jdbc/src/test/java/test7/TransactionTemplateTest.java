package test7;

import common.model.Kv;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import test7.config.DbConfig;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DbConfig.class)
@Slf4j
public class TransactionTemplateTest {

    @Autowired
    private PlatformTransactionManager txManager;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void test1() {
        TransactionTemplate txTpl = new TransactionTemplate(txManager);
        Kv tim = txTpl.execute(new TransactionCallback<Kv>() {
            @Override
            public Kv doInTransaction(TransactionStatus status) {
                log.info("inside transaction");
                return jdbcTemplate.queryForObject("SELECT k,v FROM kv WHERE k=?", new BeanPropertyRowMapper<>(Kv.class), "Tim");
            }
        });
        assertEquals("Tayler", tim.getV());
    }

    @Test
    public void testAbort() {
        Kv tim = null;

        try {
            TransactionTemplate txTpl = new TransactionTemplate(txManager);
            tim = txTpl.execute(new TransactionCallback<Kv>() {
                @Override
                public Kv doInTransaction(TransactionStatus status) {
                    return jdbcTemplate.queryForObject("SELECT bad,sql FROM kv WHERE k=?", new BeanPropertyRowMapper<>(Kv.class), "Tim");
                }
            });
        } catch (Exception e) {
            log.warn("Exception caught: " + e.getClass() + ": " + e.getMessage());
        }

        assertNull(tim);
    }
}
