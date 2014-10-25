package de.lathspell.test.cdi.helper;

import javax.inject.Singleton;

@Singleton
public class SingletonCounter {

    private int hits = 0;

    public int getCount() {
        return ++hits;
    }
}
