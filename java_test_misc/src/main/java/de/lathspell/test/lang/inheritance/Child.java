package de.lathspell.test.lang.inheritance;

/** @see StaticMethodTest */
public class Child extends Father {

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
