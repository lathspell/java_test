package de.lathspell.test.advice;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ExceptionTransformAdvice {

    @AfterThrowing(pointcut = "execution(public int de.lathspell.test.service.CalculatorService.div(..))", throwing = "e")
    public void afterThrowingInDiv(JoinPoint jp, Throwable e) {
        // Caveat: JoinPoint throws UnsupportedOperation for getSourceLocation()'s getFile() and getLine()!
        // SourceLocation loc = jp.getSourceLocation();
        log.info("Exception in {}:  {}: {}", jp.toShortString(), e.getClass(), e.getMessage());
    }

    @Around("bean(calculatorService) && execution(public * *.strangeDiv(..))")
    public Object adroundInStrangeDiv(ProceedingJoinPoint jp) {
        try {
            return jp.proceed(jp.getArgs());
        } catch (Throwable e) {
            log.info("Exception in {}: {}: {}", jp.toShortString(), e.getClass(), e.getMessage());
            return -1;
        }
    }

    @Pointcut("bean(calculatorService)")
    public void calculatorServiceBean() {
    }

    @Before(value = "calculatorServiceBean() && execution(public * *.add(..)) && args(a,b)", argNames = "a,b")
    public void beforeAdd(Object a, Object b) {
        log.info("Before add({}, {})", a, b);
    }
}
