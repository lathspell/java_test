package test11;

import common.model.Kv;
import common.model.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import test11.config.Test11Config;
import test11.service.PersonService;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Test11Config.class)
public class InsertTest {

    @Autowired
    private PersonService svc;

    @Test
    public void testCrud() {
        assertNull(svc.findByName("Schmidt"));

        long newId = svc.save(new Person(null, "Schmidt"));

        assertThat(newId, greaterThan(0L));
        assertEquals("Schmidt", svc.findByName("Schmidt").getName());
    }


}
