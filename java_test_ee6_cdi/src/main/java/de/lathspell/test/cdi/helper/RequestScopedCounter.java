package de.lathspell.test.cdi.helper;

public class RequestScopedCounter {

    private int hits = 0;

    public int getCount() {
        return ++hits;
    }
}
