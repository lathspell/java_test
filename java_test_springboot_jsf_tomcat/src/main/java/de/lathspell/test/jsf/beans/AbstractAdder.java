package de.lathspell.test.jsf.beans;

public class AbstractAdder {

    private int number = 0;

    public int inc() {
        return number++;
    }

    public int hash() {
        return hashCode();
    }
}
