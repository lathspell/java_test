package de.lathspell.test.service;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import de.lathspell.test.config.AopConfiguration;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AopConfiguration.class)
@Slf4j
public class FooTest {

    @Autowired
    private FooService fooService;
    
    @Autowired
    private FooSubService fooSubService;
    
    @Autowired
    private DebugService logService;

    @Before
    public void before() {
        logService.setLog("");
    }

    @Test
    public void test() {
        fooService.giveMeFoo();
        assertThat(logService.getLog(), is("beforeAllMethods-beforeGive"));
    }

    @Test
    public void testWithArgs() {
        fooService.giveMe("Tim");
        assertThat(logService.getLog(), is("beforeAllMethods-beforeGive-beforeGiveMeName(Tim)"));
    }

    @Test
    public void testJoinPoints() {
        fooService.hello("Tim", "Te");
        assertThat(logService.getLog(), is("beforeAllMethods-beforeHello([Tim, Te])"));

        JoinPoint jp = DebugService.lastJointPoint;
        assertThat(jp, notNullValue());
        assertThat(jp.getKind(), is("method-execution"));
        assertThat(jp.toShortString(), is("execution(FooService.hello(..))"));
        assertThat(jp.getSignature().toString(), is("String de.lathspell.test.service.FooService.hello(String,String)"));
        // Interestingly, the target seems to be our fooService but still has a different hashCode. Proxying effect?
        assertThat(fooService.toString(), is(jp.getTarget().toString()));
        assertThat(fooService.hashCode(), not(jp.getTarget().hashCode()));
    }

    @Test
    public void testRecursion() {
        fooService.defaultHello();
        // Only Pointcuts matching "defaultHello()" will be executed.
        // If defaultHello() calls hello() internally, then this is not intercepted!
        assertThat(logService.getLog(), is("beforeAllMethods"));
    }

    @Test
    public void testPackageLevel() {
        fooService.giveMePackage();
        assertThat(logService.getLog(), is("beforeAllMethods"));
    }

    @Test
    public void testProtectedLevel() {
        fooSubService.callProtected();
        assertThat(logService.getLog(), is("")); // Only methods with public and package level access can be intercepted!
    }
}
