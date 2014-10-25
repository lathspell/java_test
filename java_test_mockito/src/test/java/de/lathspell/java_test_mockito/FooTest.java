package de.lathspell.java_test_mockito;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FooTest {

    @Mock
    Foo mockedFoo;

    @Before
    public void before() {
        // Use this or @RunWith(MockitoJUnitRunner.class) to setup @Mock!
        MockitoAnnotations.initMocks(this);
    }

    /** Nothing is mocked here. */
    @Test
    public void testTheRealThing() {
        Foo foo = new Foo();
        assertEquals("hello", foo.getHello());
        assertEquals("Hello Tim!", foo.getGreeting("Tim"));
    }

    /** Simple mock of one method call. */
    @Test
    public void testSimple() {
        Foo myMockedFoo = mock(Foo.class);
        when(myMockedFoo.getHello()).thenReturn("braw!"); // prepare mock
        assertEquals("braw!", myMockedFoo.getHello());
    }

    @Test
    public void testVerify() {
        mockedFoo.getGreeting("Tim");
        verify(mockedFoo).getGreeting("Tim"); // was called once
        verify(mockedFoo, times(1)).getGreeting("Tim"); // same as above
        verify(mockedFoo, never()).getHello(); // was never called
    }

    /** What if the mock is called for unexpected values? */
    @Test
    public void testDefaultsToRealMethod() {
        Foo mockedFoo1 = mock(Foo.class);
        assertNull(mockedFoo1.getGreeting("Tom"));

        Foo mockedFoo2 = mock(Foo.class, CALLS_REAL_METHODS);
        assertEquals("Hello Tom!", mockedFoo2.getGreeting("Tom"));
    }

    /** Prepare for sudden surprises! */
    @Test
    public void testMockOnlyNthInvocation() {
        when(mockedFoo.getGreeting("Tim"))
                .thenCallRealMethod()
                .thenCallRealMethod()
                .thenReturn("bwarrrr!")
                .thenCallRealMethod(); // default for all successive calls

        assertEquals("Hello Tim!", mockedFoo.getGreeting("Tim"));
        assertEquals("Hello Tim!", mockedFoo.getGreeting("Tim"));
        assertEquals("bwarrrr!", mockedFoo.getGreeting("Tim"));
        assertEquals("Hello Tim!", mockedFoo.getGreeting("Tim"));
    }

}
