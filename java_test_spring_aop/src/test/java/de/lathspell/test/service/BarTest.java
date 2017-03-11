package de.lathspell.test.service;

import lombok.extern.slf4j.Slf4j;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import de.lathspell.test.config.AopConfiguration;
import de.lathspell.test.service.BarService;
import de.lathspell.test.service.DebugService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AopConfiguration.class)
@Slf4j
public class BarTest {

    @Autowired
    private BarService barService;

    @Autowired
    private DebugService logService;

    @Before
    public void before() {
        logService.setLog("");
    }

    @Test
    public void testAfterReturning() {
        String result = barService.hello("Tim");
        assertThat(result, is("Hello Tim"));
        assertThat(logService.getLog(), is("afterHello()-afterReturningFromHello([Tim]) => Hello Tim"));
    }

    @Test
    public void testAfterThrowing() {
        try {
            barService.hello(null);
        } catch (Exception e) {
            // ignore
        }
        assertThat(logService.getLog(), is("afterHello()-afterThrowingFromHello([null]) @" + barService.toString() + " => No name!"));
    }
}
