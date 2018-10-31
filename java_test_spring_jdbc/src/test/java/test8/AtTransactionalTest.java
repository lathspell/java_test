package test8;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import test8.config.Config;
import test8.service.KvService;

import javax.sql.DataSource;
import java.sql.Connection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class AtTransactionalTest {

    @Autowired
    private KvService kvService;


    @Test
    public void testGood() {
        assertEquals(2, kvService.countGood());
    }

    @Test
    public void testFancy() {
        assertEquals(2, kvService.countFancy());
    }

    @Test(expected = BadSqlGrammarException.class)
    public void testBad() {
        kvService.countBad();
    }

    @Test
    public void testRollback() {
        assertEquals(2, kvService.countGood());
        try {
            kvService.badTransaction();
        } catch (Exception e) {
            // Bad SQL Grammer will be thrown
        }
        assertEquals(2, kvService.countGood());
    }

    /** The readOnly attribute in @Transactional is only a optimizer hint, it does not make the JDBC Connection readonly! */
    @Test
    public void testReadOnly() throws Exception {
        JdbcTemplate tmpl = kvService.getReadonlyJdbcTemplate();
        DataSource ds = tmpl.getDataSource();
        Connection c = ds.getConnection();
        assertFalse(c.isReadOnly()); // the JDBC Connection itself is *not* read-only!

        kvService.writesDuringReadOnly();
        assertEquals(0, kvService.countGood()); // write worked despite read-only!
    }

}