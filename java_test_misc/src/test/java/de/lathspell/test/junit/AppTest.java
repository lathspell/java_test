package de.lathspell.test.junit;

import org.junit.AfterClass;
import org.junit.Test;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(SortByMethodNameRunner.class)
public class AppTest {
    private static String ran;

    @BeforeClass
    public static void beforeClass() {
        ran = "";
    }

    @AfterClass
    public static void afterClass() {
        assertEquals("ABC", ran);
    }

    @Test
    public void testB() {
        ran += "B";
        assertTrue(true);
    }

    @Test
    public void testC() {
        ran += "C";
        assertTrue(true);
    }

    @Test
    public void testA() {
        ran += "A";
        assertTrue(true);
    }
}
