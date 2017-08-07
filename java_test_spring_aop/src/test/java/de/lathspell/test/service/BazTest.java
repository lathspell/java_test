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
import de.lathspell.test.service.BazService;
import de.lathspell.test.service.DebugService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AopConfiguration.class)
@Slf4j
public class BazTest {

    @Autowired
    private BazService bazService;

    @Autowired
    private DebugService logService;

    @Before
    public void before() {
        logService.setLog("");
    }

    @Test
    public void testAround() {
        String result = bazService.hello("Tim", "Te");
        assertThat(result, is("HELLO TE TIM"));
        assertThat(logService.getLog(), is("around/before-around/after"));
    }

}
