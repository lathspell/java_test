package de.lathspell.test;

import com.ibm.icu.impl.UTS46;
import com.ibm.icu.text.IDNA;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class IcuIdnTest {

    private static final IDNA idna = IDNA.getUTS46Instance(IDNA.NONTRANSITIONAL_TO_ASCII | IDNA.NONTRANSITIONAL_TO_UNICODE);

    /**
     * Nur IDN2008 kodiert "ß" als Umlaut und nicht nur als "ss" wie IDN2003.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testIDN2008() throws Exception {
        assertEquals(UTS46.class, idna.getClass());
        assertEquals("xn--tt-giat.de", idnaToASCII("täßt.de"));
        assertEquals("xn--tt-giat.de", idnaToASCII("xn--tt-giat.de"));
        assertEquals("täßt.de", idnaToUnicode("xn--tt-giat.de"));
        assertEquals("täßt.de", idnaToUnicode("tä\u00dft.de"));
        assertEquals("täßt.de", idnaToUnicode("täßt.de")); // 'ß' = '\u00df'
    }

    private String idnaToASCII(String utf8) throws Exception {
        IDNA.Info info = new IDNA.Info();
        String result = idna.nameToASCII(utf8, new StringBuilder(), info).toString();
        assertFalse(info.getErrors().toString(), info.hasErrors());
        return result;
    }

    private String idnaToUnicode(String ace) throws Exception {
        IDNA.Info info = new IDNA.Info();
        String result = idna.nameToUnicode(ace, new StringBuilder(), info).toString();
        assertFalse(info.getErrors().toString(), info.hasErrors());
        return result;
    }
}
