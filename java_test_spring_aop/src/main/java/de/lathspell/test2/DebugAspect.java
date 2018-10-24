package de.lathspell.test2;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect            // This component should act as aspect
@Component
@Slf4j
public class DebugAspect {

    private DebugLog debugLog;

    // Constructor-Injection because we want to play around with old-style XML files that don't honor @Autowired
    public DebugAspect(@Autowired DebugLog debugLog) {
        this.debugLog = debugLog;
    }

    @Before("execution(public * MyService.*(..))")  // The PointCut definition in AspectJ Pointcut Expression Language
    public void beforeAnyMethod(JoinPoint jp) {  // JoinPoint is the actual method that was matched
        log.info("Before " + jp);

        for (Object p : jp.getArgs()) {
            log.info("Storing parameter " + p.toString());
            debugLog.getAllInputs().add(p);
        }
    }

    @Before("execution(public * MyOtherService.*(..))")
    public void beforeAnyOtherMethod(JoinPoint jp) {
        log.info("Before " + jp);

        for (Object p : jp.getArgs()) {
            log.info("Storing parameter " + p.toString());
            debugLog.getAllInputs().add(p);
        }
    }
}
