package de.lathspell.test.lang.functional;

import java.util.function.Predicate;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PredicateTest {

    @Test
    public void testPredicate() {
        assertTrue(myPredicate(4, x -> x <= 10));
    }

    /**
     * Example of a method that accepts a "predicate".
     *
     * A "predicate" is a Lambda Expression that accepts paramters and returns a boolean value.
     *
     * @param i
     * @param lambda
     * @return
     */
    private boolean myPredicate(int i, Predicate<Integer> lambda) {
        return lambda.test(i);
    }
}
