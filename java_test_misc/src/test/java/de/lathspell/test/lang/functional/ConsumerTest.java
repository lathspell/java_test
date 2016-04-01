package de.lathspell.test.lang.functional;

import java.util.function.Consumer;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConsumerTest {

    private int total = 10;

    @Test
    public void testConsumer() {
        myConsumer(4, x -> total += x * 2);
        assertEquals(18, total);
    }

    /**
     * Example of a method that accepts a "consumer".
     *
     * A "consumer" is a Lambda Expression that accepts paramters and returns void.
     * It might have "side effects" i.e. change object attributes though.
     *
     * @param i
     * @param lambda
     * @return
     */
    private void myConsumer(int i, Consumer<Integer> lambda) {
        lambda.accept(i);
    }
}
