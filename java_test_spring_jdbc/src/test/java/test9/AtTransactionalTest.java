package test9;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import test9.service.KvService;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("application-context.xml")
public class AtTransactionalTest {

    @Autowired
    private KvService kvService;

    @Test
    public void testGood() {
        assertEquals(2, kvService.countGood());
    }

    @Test
    @Sql(scripts = "../common/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testDoubleGood() {
        assertEquals(4, kvService.countGood());
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
}
