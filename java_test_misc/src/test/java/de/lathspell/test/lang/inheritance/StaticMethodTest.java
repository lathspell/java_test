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
