package de.lathspell.test4;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
@Slf4j
public class Test4 {

    @Autowired
    private MyService svc;

    @Test
    public void test() {
        assertEquals(7, svc.add(3, 4));
        assertEquals(-3L, svc.negLong(3L));
    }
}
