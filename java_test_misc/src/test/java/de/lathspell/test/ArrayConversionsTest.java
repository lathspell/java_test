package de.lathspell.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayConversionsTest {

    /** Testing the conversions between the various kinds of arrays in Java.
     *
     * <pre>
     * Q: Why gives println() this the funny "[Ljava.lang.Integer;" output?
     * A: System.out.println() calls
     *    - java.io.PrintStream.println(Object o) as there is no more specific
     *      PrintStream.println(Integer[] o) method.
     *    - String.valueOf(Object o)
     *    - Object.toString()
     *    - Object.getClass().getName() and adds "@" and hex(getHash())
     *    - Class.getName() as getClass() returns type Class&lt;T&gt;
     *    Finally Class.getName() returns this.name which is magically set
     *    by native methods in the JVM. The Class.getName() at least has a
     *    proper description which arrays generate "[[b" or "[Ljava.lang.Byte".
     * </pre>
     *
     */
    @Test
    public void testArrayConversions() {
        /**
         * Java primitive arrays
         */
        int[] ia = new int[4];
        ia[ia.length - 1] = 4;
        assertTrue(ia.toString().startsWith("[I@"));
        assertEquals("[0, 0, 0, 4]", Arrays.toString(ia));

        /**
         * Java object arrays
         */
        Integer[] iia = new Integer[] {0, 0, 0, 0}; // default is { null, null, null, null }
        iia[iia.length - 1] = 4;
        assertTrue(iia.toString().startsWith("[Ljava.lang.Integer;@"));
        assertEquals("[0, 0, 0, 4]", Arrays.deepToString(iia));

        /**
         * Java Collections
         */
        ArrayList<Integer> ci = new ArrayList<Integer>();
        Collections.addAll(ci, new Integer(0), new Integer(0), new Integer(0), new Integer(0));
        ci.set(ci.size() - 1, 4);
        assertEquals("[0, 0, 0, 4]", ci.toString());

        // Converting
        List<Integer> ci_from_ia = Arrays.asList(ArrayUtils.toObject(ia));
        List<Integer> ci_from_iia = Arrays.asList(iia);
        Integer[] iia_from_ia = ArrayUtils.toObject(ia);
        Integer[] iia_from_ci = ci.toArray(iia);
        int[] ia_from_ci = ArrayUtils.toPrimitive(ci.toArray(new Integer[0]));
        int[] ia_from_iia = ArrayUtils.toPrimitive(iia);
        assertEquals(ci, ci_from_ia);
        assertEquals(ci, ci_from_iia);
        assertArrayEquals(iia, iia_from_ia);
        assertArrayEquals(iia, iia_from_ci);
        assertArrayEquals(ia, ia_from_ci);
        assertArrayEquals(ia, ia_from_iia);
    }
}
