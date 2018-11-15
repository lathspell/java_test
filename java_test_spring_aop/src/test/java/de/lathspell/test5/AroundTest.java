package de.lathspell.test5;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Config.class)
public class AroundTest {

    @Autowired
    private FifthService svc;

    @Test(expected = RuntimeException.class)
    public void test0() {
        svc.start(0);
    }

    @Test
    public void test1() {
        assertEquals(-1, svc.start(1)); // @Around prevents call
        assertEquals(-2, svc.start(2)); // @Around modifies successful call
        assertEquals(-3, svc.start(3)); // @Around traps exception
        assertEquals(8, svc.start(4)); // @Around passes'
    }

}
