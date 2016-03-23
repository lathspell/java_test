
import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

import de.lathspell.test.Application;
import de.lathspell.test.model.FooCdiApplicationScoped;
import de.lathspell.test.model.FooCdiDependentScoped;
import de.lathspell.test.model.FooCdiRequestScoped;
import de.lathspell.test.model.FooSpringPrototype;
import de.lathspell.test.model.FooSpringSingleton;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class CdiTest {

    @Inject
    private FooCdiDependentScoped fooCdiDep1;

    @Inject
    private FooCdiDependentScoped fooCdiDep2;

    @Inject
    private FooCdiRequestScoped fooCdiReq1;

    @Inject
    private FooCdiRequestScoped fooCdiReq2;

    @Inject
    private FooCdiApplicationScoped fooCdiApp1;

    @Inject
    private FooCdiApplicationScoped fooCdiApp2;

    @Inject
    private FooSpringPrototype fooSpringProt1;

    @Inject
    private FooSpringPrototype fooSpringProt2;

    @Inject
    private FooSpringSingleton fooSpringSingle1;

    @Inject
    private FooSpringSingleton fooSpringSingle2;

    @Test
    public void testCdiDependendScoped() {
        assertEquals(fooCdiDep1.getId(), fooCdiDep2.getId(), 0.0); // Beware!
    }

    @Test
    public void testCdiRequestScoped() {
        assertEquals(fooCdiReq1.getId(), fooCdiReq2.getId(), 0.0);
    }

    @Test
    public void testCdiApplicationScoped() {
        assertEquals(fooCdiApp1.getId(), fooCdiApp2.getId(), 0.0);
    }

    @Test
    public void testSpringPrototype() {
        assertNotEquals(fooSpringProt1.getId(), fooSpringProt2.getId(), 0.0);
    }

    @Test
    public void testSpringSingleton() {
        assertEquals(fooSpringSingle1.getId(), fooSpringSingle2.getId(), 0.0);
    }
}
