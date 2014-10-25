package de.lathspell.test.lang.inheritance;

/** @see StaticMethodTest */
public class Father {

    public static String staticProducer() {
        return "Father";
    }

    public Father(String x) {

    }

    public String instanceProducer() {
        return "Father";
    }
}
