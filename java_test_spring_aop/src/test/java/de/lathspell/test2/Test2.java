package de.lathspell.test2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class Test2 {

    @Autowired
    private MyService svc;

    @Autowired
    private MyOtherService otherSvc;

    @Autowired
    private DebugLog debugLog;

    @Test
    public void test1() {
        assertEquals(5, svc.add(2, 3));
        assertEquals(Arrays.asList(2,3), debugLog.getAllInputs());
    }

    @Test
    public void test2() {
        assertEquals(-1, otherSvc.sub(2, 3));
        assertEquals(Arrays.asList(2,3,2,3), debugLog.getAllInputs());
    }
}
