package de.lathspell.test;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class FibonacciTest {

    @Test
    public void testIterative() {
        assertEquals("[1, 1, 2, 3, 5, 8, 13]", Fibonacci.fibIterative(7).toString());
    }

    @Test
    public void testRecursive() {
        assertEquals("[1, 1, 2, 3, 5, 8, 13]", Fibonacci.fibRecursive(7).toString());
    }
}
