package de.lathspell.test.net;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.Ignore;

import jp.co.jprs.idnkit.IDNAFactory;

/** The class IDNA is in both the GNU and JPRS namespace! */
public class IDNTest {
    /** Test mit IDN ohne "ß".
     *
     * Mit der Library die bei OX mitgeliefert ist.
     * Die ACE Strings müssen mit xn-- anfangen.
     */
    @Test
    public void testIDN() throws Exception {
        assertEquals("täst.de", gnu.inet.encoding.IDNA.toUnicode("xn--tst-qla.de"));
        assertEquals("täst.de", gnu.inet.encoding.IDNA.toUnicode("täst.de"));
        assertEquals("xn--tst-qla.de", gnu.inet.encoding.IDNA.toASCII("täst.de"));
        assertEquals("xn--tst-qla.de", gnu.inet.encoding.IDNA.toASCII("xn--tst-qla.de"));
    }

    /** Test mit idnkit für IDN2008 mit "ß".
     *
     * Braucht leider com/sun/jni/Library.
     */
    @Ignore
    public void testIDN2008() throws Exception {
        jp.co.jprs.idnkit.IDNA idna = IDNAFactory.create(IDNAFactory.IDNA2008);
        System.out.println("version: " + idna.getVersion());

        assertEquals("foo@täßt.de", idna.decode("foo@xn--tt-giat.de"));
        assertEquals("foo@xn--tt-giat.de", idna.encode("foo@täßt.de"));
        assertEquals("foo@täßt.de", idna.decode("foo@täßt.de"));
        assertEquals("foo@xn--tt-giat.de", idna.encode("foo@xn--tt-giat.de"));
    }
}
