package de.lathspell.test.services;

import lombok.extern.slf4j.Slf4j;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = NONE)
@Slf4j
public class TestMyCtor {

    @Autowired
    private MyCtor myCtor;

    @Test
    public void testBeanWithCtorInjection() {
        assertEquals("Tom", myCtor.getName());
    }
}
