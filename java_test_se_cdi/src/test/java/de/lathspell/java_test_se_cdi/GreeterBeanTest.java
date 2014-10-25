package de.lathspell.java_test_se_cdi;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

import de.lathspell.java_test_se_cdi.config.ApplicationConfig;

public class GreeterBeanTest {

    private static WeldContainer container;
    private GreeterBean greeterBean;

    @BeforeClass
    public static void beforeClass() throws Exception {
        ApplicationConfig.setEnv("test");
        Weld weld = new Weld();
        container = weld.initialize();
    }

    @Before
    public void before() {
        greeterBean = container.instance().select(GreeterBean.class).get();
    }

    @Test
    public void testApp() {
        assertNotNull("@Inject has apparently failed for greeterBean!", greeterBean);
        assertEquals("Hello Tom Test", greeterBean.getGreeting());
    }
}
