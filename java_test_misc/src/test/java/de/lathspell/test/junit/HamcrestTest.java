package de.lathspell.test.junit;

import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.*;

public class HamcrestTest {

    @Test
    public void testLists() {
        List<String> abc = Arrays.asList("a", "b", "c");

        // "is" is universally usable, even for lists
        assertThat(abc, is(asList("a", "b", "c")));
        assertThat(abc, is(not(asList("b", "c", "a"))));

        // "contains" means "contains exactly"!
        assertThat(abc, contains(asList("a", "b", "c").toArray()));
        assertThat(abc, not(contains(asList("a", "b").toArray())));
        assertThat(abc, containsInAnyOrder(asList("b", "c", "a").toArray()));
    }

}
