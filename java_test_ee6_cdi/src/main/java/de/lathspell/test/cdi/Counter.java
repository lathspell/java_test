package de.lathspell.test.cdi;

import javax.inject.Inject;

import de.lathspell.test.cdi.helper.RequestScopedCounter;
import de.lathspell.test.cdi.helper.SingletonCounter;

/**
 * Test for @Singleton.
 */
public class Counter {

    @Inject
    RequestScopedCounter rcounter;
    @Inject
    SingletonCounter scounter;

    public int getSingletonCount() {
        return scounter.getCount();
    }

    public int getRequestCounter() {
        return rcounter.getCount();
    }
}
