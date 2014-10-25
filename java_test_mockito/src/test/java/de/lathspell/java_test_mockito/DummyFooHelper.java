package de.lathspell.java_test_mockito;

import javax.enterprise.inject.Alternative;

/** For CDIMockingTest. */
@Alternative
public class DummyFooHelper extends FooHelper {

    @Override
    public String getUser() {
        return "DummyUser";
    }
}
