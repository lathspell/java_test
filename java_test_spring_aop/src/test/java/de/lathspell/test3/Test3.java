package de.lathspell.test3;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@Slf4j
public class Test3 {

    private ApplicationContext ctx;

    public Test3() {
        log.info("Loading test3.xml");
        ctx = new ClassPathXmlApplicationContext("test3.xml");
    }

    @Test
    public void testTheOldTimes() {
        log.info("entering test");
        DebugLog debugLog = ctx.getBean(DebugLog.class);

        log.info("testing MyService");
        MyService svc = ctx.getBean(MyService.class);
        assertEquals(7, svc.add(3, 4));
        assertEquals(Arrays.asList(3, 4), debugLog.getAllInputs());

        log.info("testing MyOtherService");
        MyOtherService otherSvc = ctx.getBean(MyOtherService.class);
        assertEquals(11, svc.add(5, 6));
        assertEquals(Arrays.asList(3, 4, 5, 6), debugLog.getAllInputs());

    }
}
