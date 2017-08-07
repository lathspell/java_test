package de.lathspell.test.aspects;

import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.lathspell.test.service.BarService;
import de.lathspell.test.service.DebugService;

@Aspect
@Component
@Slf4j
public class BarAspects {

    @Autowired
    private DebugService debugService;

    /**
     * Execute regardless whether the method returns normally or by throwing an Exception.
     */
    @After(value = "execution(public * de.lathspell.test.service.BarService.hello(..))")
    public void afterHello() {
        log.info(debugService.append("afterHello()"));
    }
    
    /**
     * Execute if method returns normally.
     */
    @AfterReturning(value = "execution(public * de.lathspell.test.service.BarService.hello(..))", returning = "result")
    public void afterReturningFromHello(JoinPoint jp, String result) {
        log.info(debugService.append("afterReturningFromHello(" + Arrays.deepToString(jp.getArgs()) + ") => " + result));
    }

    /**
     * Execute if method throws an Exception.
     */
    @AfterThrowing(value = "execution(public * de.lathspell.test.service.BarService.hello(..)) && target(target)", throwing = "e")
    public void afterThrowingFromHello(JoinPoint jp, BarService target, Throwable e) {
        log.info(debugService.append("afterThrowingFromHello(" + Arrays.deepToString(jp.getArgs()) + ") @" + target.toString() + " => " + e.getMessage()));
    }

}
