package de.lathspell.test;

import kotlin.Pair;
import kotlin.Triple;
import org.junit.Test;
import kotlin.text.StringsKt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class StringsTest {
    @Test
    public void testStrings() {
        assertEquals("Foo bar", StringsKt.capitalize("foo bar"));

        assertTrue(StringsKt.endsWith("fOo", "oo", true));
        assertFalse(StringsKt.endsWith("fOo", "oo", false));

        assertEquals("foo", StringsKt.trimIndent("  foo"));
    }

    @Test
    public void testRegex() {
        // JDK
        Matcher m = Pattern.compile("^[a-z](.*)").matcher("abcde");
        m.matches();
        String foundJdk = m.group(1);
        assertEquals("bcde", foundJdk);

        // Kotlin
        String found = new kotlin.text.Regex("^[a-z](.*)").matchEntire("abcde").getGroupValues().get(1);
        assertEquals("bcde", found);
    }

    @Test
    public void testTuples() {
        Pair<String, Integer> p = new Pair<>("a", 40);
        assertEquals(new Integer(40), p.getSecond());

        Triple<String, Integer, Boolean> t = new Triple<>("a", 3, false);
        assertEquals(new Integer(3), t.getSecond());
    }

}
