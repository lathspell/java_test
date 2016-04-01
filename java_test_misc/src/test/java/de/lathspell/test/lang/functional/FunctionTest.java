package de.lathspell.test.lang.functional;

import java.util.function.Function;
import java.util.function.Predicate;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FunctionTest {

    @Test
    public void testPredicate() {
        assertEquals(2.0, myFunction(4, x -> x / 2.0), 0.00);
    }

    /**
     * Example of a method that accepts a "function".
     *
     * A "function" is a Lambda Expression that accepts paramters and returns a value of the specified type.
     *
     * @param i
     * @param lambda
     * @return
     */
    private double myFunction(int i, Function<Integer, Double> lambda) {
        return lambda.apply(i);
    }
}
