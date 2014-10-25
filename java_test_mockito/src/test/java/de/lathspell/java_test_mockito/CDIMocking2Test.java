package de.lathspell.java_test_mockito;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.ProducesAlternative;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Mocking of classes that are only used indirectly.
 *
 * Uses CDI-Unit and Mockito.
 *
 * We only use class Foo which itself uses an injected instance of FooHelper
 * which we cannot access directly. Luckily @ProducesAlternative can help here.
 *
 */
@RunWith(CdiRunner.class)
public class CDIMocking2Test {

    @Inject
    Foo foo;

    @Produces
    @ProducesAlternative
    @Mock
    FooHelper mockedFooHelper;

    @Test
    public void test() {
        // The real fooHelper would have returned "Alice" as user!
        when(mockedFooHelper.getUser()).thenReturn("DummyUser2");
        assertEquals("DummyUser2: hello", foo.getUserMsg("hello"));
    }

}
