package de.lathspell.test.text;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RegExTest {

    /**
     * Only the last match of the outer group is retained.
     * 
     * This is unlike e.g. preg_split() in PHP.
     * 
     * See "Groups and capturing" in the Pattern API description:     *
     * <quote>
     *  The captured input associated with a group is always the 
     *  subsequence that the group most recently matched.
     * </quote>
     * @see <a ref="Pattern">http://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html</a>
     */
    @Test
    public void testMultiGroups() {
        String s = "XfooY\nXbarY";
        
        Pattern p = Pattern.compile("^(X([a-z]+)Y\\s*)+$");
        Matcher m = p.matcher(s);
        assertTrue(m.matches());
        assertEquals(2, m.groupCount());
        assertEquals(s, m.group(0)); // original
        assertEquals("XbarY", m.group(1)); // last match of outer group
        assertEquals("bar", m.group(2)); // last match of inner group
    }
    
    @Test
    public void testDotAll() {
        String s = "test 123\n";
        assertFalse(s.matches("^test.*")); // "." doesn't include "\n"
        assertFalse(s.matches("^test.*$")); // "." doesn't include "\n"
        assertTrue(s.matches("^test.*\n$")); // explicit "\n"
        assertTrue(s.matches("(?s)^test.*")); // "(?s)" activates Pattern.DOTALL
        assertTrue(Pattern.compile("^test.*", Pattern.DOTALL).matcher(s).matches());
    }

    @Test
    public void testMultiline() {
        // Es wird mehrfach vom \n bis zum Ende der Zeile ersetzt. Klappt aber auch.
        String response = "Zeile1\nZeile2\nZeile3\n\n\n";
        assertEquals("Zeile1", response.replaceAll("\n.*", ""));

        response = "ACK 1\nRESPONSE 1 FOO\nOK\nEOR\n";
        Pattern p = Pattern.compile("^RESPONSE.*(\\d+)", Pattern.MULTILINE);
        Matcher m = p.matcher(response);
        assertTrue(m.find());
        // Die matches() Methode will immer den gesamten Eingabestring matchen,
        // was prinzipiell nicht vereinbar mit MULTILINE ist.
        assertFalse(m.matches());
        assertFalse(response.matches("(?m)^RESPONSE")); // auch kein MULTILINE mit Tricks
        assertFalse(response.matches("(?m).*RESPONSE.*")); // auch nicht
        assertFalse(response.matches(".*RESPONSE.*")); // "." matche ja kein Zeilenende
        assertTrue(response.matches("(?s).*RESPONSE 1.*")); // aber als Singleline-Match geht es!
        assertTrue(response.matches("(?s).*\nRESPONSE 1 FOO\n.*")); // aber als Singleline-Match geht es!
    }

    @Test
    public void testMultilineRegexReplace() {
        String csv = "4,2011-11-15,a\n2,2011-11-15,b";
        csv = csv.replaceAll("(?m)^(\\d+),[^,]+,", "$1,xxx,"); // Datum maskieren
        assertEquals("4,xxx,a\n2,xxx,b", csv);
    }

    @Test
    public void testGrepReplace() {
        String s = "# good1\nbad1\nbad2\n# good2";
        String soll = "# good1\\n# good2";
        // enthält ein "\n" pro bad Zeile: String ist = s.replaceAll("(?m)^[^#].*\n?$", "");
        String ist = StringUtils.join(CollectionUtils.select(
                Arrays.asList(s.split("\n")),
                new Predicate() {

                    @Override
                    public boolean evaluate(Object input) {
                        return ((String) input).startsWith("#");
                    }
                }), "\\n"); // escaptes Newline
        assertEquals(ist, soll);
    }

    @Test
    public void testIndent() {
        assertEquals("1\n    2\n    3", "1\n2\n3\n".replaceAll("\n", "\n    ").trim());
    }

    @Test
    public void testMatches() {
        // Der Match muss komplett sein.
        assertTrue("foo".matches("foo"));
        assertTrue("foo".matches("^foo$"));
        assertFalse("xxfooxx".matches("foo"));
    }

    @Test
    public void testForOpenNMS2() {
        String regex = "^(|(?!R-MD).*)$";
        assertFalse("R-MD-foo".matches(regex));
        assertTrue("22116804109".matches(regex));
        assertTrue("X".matches(regex));
        assertTrue("".matches(regex));
    }

    @Test
    public void testGetBack() {
        String nr = "4922112345";

        Pattern p = Pattern.compile("^49(221)(.*)");
        Matcher m = p.matcher(nr);
        boolean found = m.find();

        assertEquals(found, true);
        assertEquals(m.group(0), "4922112345");
        assertEquals(m.group(1), "221");
        assertEquals(m.group(2), "12345");
    }

    @Test
    public void test2() {
        String sn = "004011-123456";
        Matcher m = Pattern.compile("^.*-(.*)").matcher(sn);
        assertTrue(m.find());
        assertEquals("123456", m.group(1));
        assertEquals("123456", sn.replaceFirst(".*-", ""));

        sn = "123456";
        m = Pattern.compile("^.*-(.*)").matcher(sn);
        assertFalse(m.find());
        assertEquals("123456", sn.replaceFirst(".*-", ""));
    }

    @Test
    public void testUmlauts() {
        String usrUidRegexp = "[$@%\\.+a-zäöüßA-ZÄÖÜ0-9_-]";
        String userName = "foo@täst.de";
        String illegal = userName.replaceAll(usrUidRegexp, "");
        assertEquals("", illegal);
    }

    @Test
    public void testCrypt3() {
        Pattern p = Pattern.compile("^(\\$5\\$(rounds=(\\d+)\\$)?)?([\\.\\/a-zA-Z0-9]{1,16}).*");

        Matcher m = p.matcher("$5$secret");
        assertTrue(m.find());
        assertEquals(4, m.groupCount());
        assertEquals("$5$", m.group(1));
        assertEquals(null, m.group(2));
        assertEquals(null, m.group(3));
        assertEquals("secret", m.group(4));

        m = p.matcher("$5$secret$");
        assertTrue(m.find());
        assertEquals("$5$", m.group(1));
        assertEquals(null, m.group(2));
        assertEquals(null, m.group(3));
        assertEquals("secret", m.group(4));

        m = p.matcher("$5$12345678901234567890$foobar");
        assertTrue(m.find());
        assertEquals("$5$", m.group(1));
        assertEquals(null, m.group(2));
        assertEquals(null, m.group(3));
        assertEquals("1234567890123456", m.group(4));

        m = p.matcher("secret");
        assertTrue(m.find());
        assertEquals(null, m.group(1));
        assertEquals(null, m.group(2));
        assertEquals(null, m.group(3));
        assertEquals("secret", m.group(4));

        m = p.matcher("$5$rounds=1234$foo");
        assertTrue(m.find());
        assertEquals("$5$rounds=1234$", m.group(1));
        assertEquals("rounds=1234$", m.group(2));
        assertEquals("1234", m.group(3));
        assertEquals("foo", m.group(4));

        m = p.matcher("$5$rounds=2$mmmm./Ag0s$rest");
        assertTrue(m.find());
        assertEquals("$5$rounds=2$", m.group(1));
        assertEquals("rounds=2$", m.group(2));
        assertEquals("2", m.group(3));
        assertEquals("mmmm./Ag0s", m.group(4));

        m = p.matcher("====");
        assertFalse(m.find());

        m = p.matcher("$5$rounds=KKKK$secret$");
        assertTrue(m.find());
        assertEquals("$5$", m.group(1));
        assertEquals(null, m.group(2));
        assertEquals(null, m.group(3));
        assertEquals("rounds", m.group(4));

        m = p.matcher("");
        assertFalse(m.find());
    }
}
