package test8;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import test8.config.Config;
import test8.service.KvService;

import static org.junit.Assert.assertEquals;

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
}
