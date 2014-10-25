package de.lathspell.java_test_mockito;

import javax.inject.Inject;

import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

/**
 * Mocking of classes that are only used indirectly.
 *
 * Uses CDI-Unit @ActivatedAlternative but not Mockito.
 *
 * We only use class Foo which itself uses an injected instance of FooHelper
 * which we cannot access directly. Luckily @ProducesAlternative can help here.
 */
@RunWith(CdiRunner.class)
@ActivatedAlternatives(DummyFooHelper.class)
public class CDIMocking1Test {

    @Inject
    Foo foo;

    @Test
    public void test() {
        // The real fooHelper would have returned "Alice" as user!
        assertEquals("DummyUser: hello", foo.getUserMsg("hello"));
    }

}
