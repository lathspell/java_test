package de.lathspell.java_test_se_cdi;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.runners.BlockJUnit4ClassRunner;

/**
 * JUnit4 runner for CDI with JBoss Weld.
 *
 * Did not work for me though.
 *
 * @see https://community.jboss.org/wiki/CreatingUnitTestsWithWeldAndJunit4
 */
public class WeldJUnit4Runner extends BlockJUnit4ClassRunner {

    private final Class<?> klass;
    private final Weld weld;
    private final WeldContainer container;

    public WeldJUnit4Runner(Class<Object> klass) throws Exception {
        super(klass);
        System.out.println("Runner ctor");
        this.klass = klass;
        this.weld = new Weld();
        this.container = weld.initialize();
        System.out.println("Runner ctor exit");
    }

    @Override
    protected Object createTest() throws Exception {
        System.out.println("Runner createTest");
        return container.instance().select(klass).get();
    }
}
