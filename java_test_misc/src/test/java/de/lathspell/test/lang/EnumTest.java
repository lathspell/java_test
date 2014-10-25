package de.lathspell.test.lang;

import org.junit.Test;
import static org.junit.Assert.*;
import static de.lathspell.test.lang.EnumTest.EwuTree.*;

public class EnumTest {

    enum EwuTree {
        TEST,
        DEVEL,
        BETA,
        RELEASE;
        
        public boolean isImportant() {
            return this == BETA || this == RELEASE;
        }
    }

    @Test
    public void testEnums() {
        EwuTree tree = BETA;
        assertNotSame(DEVEL, tree);

        EwuTree tree2 = EwuTree.valueOf("devel".toUpperCase());
        assertEquals(DEVEL, tree2);

        assertEquals("DEVEL", DEVEL.toString());
        
        assertFalse(DEVEL.isImportant());
        assertTrue(RELEASE.isImportant());
    }
}
