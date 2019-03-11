package de.lathspell.test.generators;

import java.util.Random;

public class RandomDoubleGenerator {

    private final Random rnd = new Random();

    public double nextDouble() {
        return rnd.nextDouble();
    }
}
