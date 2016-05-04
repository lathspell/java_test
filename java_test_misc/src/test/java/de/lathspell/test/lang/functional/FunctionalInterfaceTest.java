package de.lathspell.test.lang.functional;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Objects that implement interfaces with only one method can be replaced with lambdas.
 *
 * The acceptOldif() method needs an object that implements the OldIf interface.
 * In this case, it is passed a Lambda Expression instead.
 *
 * Since Java 8 it's possible to annotate such interface with @FunctionalInterface to
 * prevent anybody from adding a second method to the interface and thus breaking this
 * feature.
 */
public class FunctionalInterfaceTest {

    interface OldIf {

        public abstract String action(String name, int i);
    }

    @FunctionalInterface
    interface NewIf {

        public abstract String action(String name, int i);
    }

    @Test
    public void test() {
        String fromOld = acceptOldIf("Foo", 3, (String name, int i) -> {
            return name.toUpperCase() + i * 2;
        });
        assertEquals("FOO6", fromOld);
        String fromNew = acceptNewIf("Foo", 3, (String name, int i) -> {
            return name.toLowerCase() + i * 3;
        });
        assertEquals("foo9", fromNew);
    }

    private String acceptOldIf(String name, int i, OldIf oldIf) {
        return oldIf.action(name, i);
    }

    private String acceptNewIf(String name, int i, NewIf newIf) {
        return newIf.action(name, i);
    }
}
