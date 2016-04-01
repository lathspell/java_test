package de.lathspell.test.lang.inheritance;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StaticMethodTest {

    @Test
    public void test() {
        Father myFather = new Father("x");
        Child myChild = new Child("x");
        Father myGrownup = new Child("x");

        assertEquals("Father", Father.staticProducer());
        assertEquals("Child", Child.staticProducer());

        // Do not access static methods like instance methods!
        assertEquals("Father", myFather.staticProducer());
        assertEquals("Child", myChild.staticProducer());
        assertEquals("Father", myGrownup.staticProducer()); // because it was declared as Father!

        assertEquals("Father", myFather.instanceProducer());
        assertEquals("Child", myChild.instanceProducer());
        assertEquals("Child", myGrownup.instanceProducer());
    }
}

class Father {

    public static String staticProducer() {
        return "Father";
    }

    public Father(String x) {

    }

    public String instanceProducer() {
        return "Father";
    }
}

class Child extends Father {

    /**
     * All constructors from inherited class have to be provided in child classes, too.
     *
     * The no-arg constructor is only implicitly created if no other constructor
     * was provided, so there is none in this class!
     */
    public Child(String x) {
        super(x);
    }

    /** This method does not override the inherited produced, it's just
     * a method of the same name that "hides" the one from the superclass. */
    public static String staticProducer() {
        return "Child";
    }

    public void checkInheritedStaticMethods() {
        // this works
        Child.staticProducer();
    }

    @Override
    public String instanceProducer() {
        return "Child";
    }
}
