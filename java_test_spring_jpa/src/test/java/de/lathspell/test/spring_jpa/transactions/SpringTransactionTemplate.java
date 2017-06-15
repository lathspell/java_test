package de.lathspell.test.spring_jpa.transactions;

import lombok.extern.slf4j.Slf4j;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.PlatformTransactionManager;
import static org.springframework.transaction.TransactionDefinition.PROPAGATION_REQUIRED;
import org.springframework.transaction.support.TransactionTemplate;

import de.lathspell.test.config.AppConfiguration;
import de.lathspell.test.model.User;

/**
 * Aprogrammatic way to use Spring transactions.
 *
 * It's less preferred to @Transactional as it couples the code tighter to Spring.
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("postgres") // "h2" or "postgres"
@ContextConfiguration(classes = AppConfiguration.class)
@Slf4j
public class SpringTransactionTemplate {

    @Autowired
    private JdbcTemplate tpl;

    @Autowired
    private PlatformTransactionManager ptm;

    private TransactionTemplate tt;

    @Before
    public void before() {
        tt = new TransactionTemplate(ptm);
        tt.setName("my_tx");
        tt.setPropagationBehavior(PROPAGATION_REQUIRED);
    }

    /**
     * FIXME: Does not seem to use any transactions at all.
     */
    @Test
    public void testSpring1() {
        String result = tt.execute((status) -> {
           
            tpl.update("DELETE FROM users");
            tpl.update("INSERT INTO users (id, username, version, created_at) VALUES (default, ?, 1, now())", "Tim");
            User u = tpl.query("SELECT * FROM users WHERE username = ?", new BeanPropertyRowMapper<>(User.class), "Tim").get(0);
            return u.getUsername();
        });

        assertEquals("Tim", result);
    }

}
