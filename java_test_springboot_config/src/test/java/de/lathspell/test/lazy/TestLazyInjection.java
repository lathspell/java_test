package de.lathspell.test.lazy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Lazy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

import de.lathspell.test.Application;
import de.lathspell.test.lazy.Bean1;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class TestLazyInjection {

    /** Not eagerly initialized as there is @Lazy at injection point and it's not a scope singleton bean. */
    @Lazy
    @Autowired
    private Bean1 bean1;

    /** Eagerly initialized as there is no @Lazy at injection point. */
    @Autowired
    private Bean2 bean2;

    /** Eagerly initialized as it has no @Lazy at the injection point (@Lazy at class definitian has no effect for scope prototype beans). */
    @Autowired
    private Bean3 bean3;

    /** Not eagerly initialized despite beeing of scope singleton as it has @Lazy here and at class level. */
    @Lazy
    @Autowired
    private Bean4 bean4;

    /** Eagerly initialized despite @Lazy as it is of scope singleton. */
    @Lazy
    @Autowired
    private Bean5 bean5;

    /**
     * Examply of the @Lazy annotation.
     *
     * Check output to see which beans are actually instanciated.
     * Any attempt to use assert() on them would instanciate them.
     * "Schroedinger's beans" :-)
     *
     * Beware that SCOPE_SINGLETON beans, which are the default, are usually all automatically
     * instanciated at start. That's not documented but currently implemented. Our test beans
     * therefore all have SCOPE_SINGLETON.
     *
     * - The @Lazy @Autowired ones are not instantiated.
     * - The @Autowired bean3 which has @Lazy @Component is!
     *
     * 2016-04-06 19:15:46.602 INFO 32290 --- [ main] de.lathspell.test.lazy.Bean5 : ctor
     * 2016-04-06 19:15:46.607 INFO 32290 --- [ main] de.lathspell.test.lazy.Bean5a : ctor
     * 2016-04-06 19:15:46.692 INFO 32290 --- [ main] d.lathspell.test.lazy.TestLazyInjection : Started TestLazyInjection in 1.138 seconds (JVM running for 1.625)
     * 2016-04-06 19:15:46.704 INFO 32290 --- [ main] de.lathspell.test.lazy.Bean2 : ctor
     * 2016-04-06 19:15:46.706 INFO 32290 --- [ main] de.lathspell.test.lazy.Bean2a : ctor
     * 2016-04-06 19:15:46.718 INFO 32290 --- [ main] de.lathspell.test.lazy.Bean3 : ctor
     * 2016-04-06 19:15:46.721 INFO 32290 --- [ main] de.lathspell.test.lazy.Bean3a : ctor
     *
     */
    @Test
    public void testConfigurationBean() {
        assertTrue(true);
    }
}
