package de.lathspell.test.java_test_ee6_ejb.ejb;

import de.lathspell.test.java_test_ee6_ejb.ejb.MemoBean;
import de.lathspell.test.java_test_ee6_ejb.ejb.Memo;
import javax.ejb.embeddable.EJBContainer;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class MemoBeanTest {

    private static final Logger logger = LogManager.getLogger(MemoBean.class);
    private static EJBContainer container;
    private Memo instance;

    @BeforeClass
    public static void setUpClass() throws Exception {
        logger.entry();
        container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
    }

    @Before
    public void setUp() throws Exception {
        logger.entry();
        instance = (Memo) container.getContext().lookup("java:global/classes/MemoBean");
    }

    @Test
    public void testCRUD() throws Exception {
        logger.entry();

        instance.clear();
        assertArrayEquals(new String[]{}, instance.getAll().toArray(new String[0]));

        instance.addItem("foo");
        assertArrayEquals(new String[]{"foo"}, instance.getAll().toArray(new String[0]));

        instance.addItem("bar");
        assertArrayEquals(new String[]{"foo", "bar"}, instance.getAll().toArray(new String[0]));

        instance.removeItem("foo");
        assertArrayEquals(new String[]{"bar"}, instance.getAll().toArray(new String[0]));
    }

    /**
     * I thought that
     *
     * @Stateful would make these identical but apperently not so.
     */
    @Test
    public void testStatefullness() throws Exception {
        logger.entry();

        String first = instance.toString();
        String second = ((Memo) container.getContext().lookup("java:global/classes/MemoBean")).toString();
        assertNotEquals(first, second);
    }
}