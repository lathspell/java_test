package de.lathspell.test4;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect            // This component should act as aspect
@Component
@Slf4j
public class DebugAspect {

    @Pointcut("@target(org.springframework.stereotype.Service)")
    public void targetIsService() {
    }

    @Pointcut("args(int,..)")
    public void targetFirstParamIsInt() {
    }

    @Before("targetIsService() && targetFirstParamIsInt()")
    public void beforeService(JoinPoint jp) {  // JoinPoint is the actual method that was matched
        log.info("Before a method with first parameter Int in an instance of Service: " + jp);
    }

}
