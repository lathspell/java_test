package de.lathspell.test.aspects;

import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.lathspell.test.service.DebugService;

@Aspect
@Component
@Slf4j
public class FooAspects {

    @Autowired
    private DebugService debug;

    /**
     * Execute this method before all method calls in Fooservice.
     *
     * All public methods, that is. Interception other methods is not supported by Spring.
     */
    @Before("execution(* de.lathspell.test.service.FooService.*(..))")
    public void beforeAllMethods() {
        log.info(debug.append("beforeAllMethods"));
    }

    /**
     * Execute before calls to FooService.giveMe(String name) and pass the parameter.
     *
     * @param name The argument's name must be the same as written in the Pointcut "args" expression!
     */
    @Before("execution(public * de.lathspell.test.service.FooService.giveMe(..)) && args(name)")
    public void beforeGiveMeName(String name) {
        log.info(debug.append("beforeGiveMeName(" + name + ")"));
    }

    /**
     * Execute before calls to FooService.hello() and inspect the argument list.
     *
     * @param jp All information about the method call.
     */
    @Before("execution(public String de.lathspell.test.service.FooService.hello(..))")
    public void beforeHello(JoinPoint jp) {
        DebugService.lastJointPoint = jp;
        log.info(debug.append("beforeHello(" + Arrays.deepToString(jp.getArgs()) + ")"));
    }

    /**
     * Use gimmeFunc as Alias for "method call to public gimme*()" in FooService.
     */
    @Pointcut("execution(public * de.lathspell.test.service.FooService.give*(..))")
    public void give() {
        // empty
    }

    /**
     * Execute this before alle methods of FooService starting with give*.
     *
     * It uses the above specified Pointcut as abbreviation. The "or else" part is just to show that more complex statments are possible.
     */
    @Before("give() || execution(* de.lathspell.test.service.FooService.doesNotExist())")
    public void beforeGive() {
        log.info(debug.append("beforeGive"));
    }

}
