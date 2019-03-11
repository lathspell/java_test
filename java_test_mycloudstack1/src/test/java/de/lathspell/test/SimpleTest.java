package de.lathspell.test;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

import de.lathspell.test.generators.RandomDoubleGenerator;


public class SimpleTest {

    @Test
    public void test1() {
        RandomDoubleGenerator rdg = new RandomDoubleGenerator();
        assertThat(rdg.nextDouble()).isGreaterThan(0.0);
    }
}
