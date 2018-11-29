package de.lathspell.test;

import lombok.extern.slf4j.Slf4j;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Spring Expression Language (SpEL) Tests.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpELTest.AnonymousConfig.class)
@Slf4j
public class SpELTest {

    @Configuration
    static class AnonymousConfig {
    }

    /**
     * A literal string value.
     */
    @Value("Tim")
    private String name1;

    /**
     * Uses a method on a literal string value.
     */
    @Value("#{'Tim'.toLowerCase()}")
    private String name2;

    /**
     * Uses the type "Math" to access a static method.
     */
    @Value("#{T(Math).random()}")
    private double rnd;

    /**
     * Uses the type "Math" to access a static method.
     */
    @Value("#{1 * 2 + 3 - 4}")
    private double math1;
    
    @Test
    public void test1() {
        assertEquals("Tim", name1);
        assertEquals("tim", name2);
        log.info("rnd={}", rnd);
        assertNotEquals(0, rnd);
        assertEquals(1.0, math1, 0.01);
    }

}
