package de.lathspell.test5;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect            // This component should act as aspect
@Component
@Slf4j
class DebugAspect {

    @Around("within(de.lathspell.test5.*) && execution(* FifthService.start(int)) && args(i)")
    public Object around(ProceedingJoinPoint jp, int i) {
        log.info("calc started with i={}", jp.getArgs(), i);
        if (i == 0) {
            throw new RuntimeException("zerooo!");
        }
        try {
            if (i == 1) {
                log.info("calc completely bypassed");
                return -1;
            }
            Object result = jp.proceed(jp.getArgs());
            log.info("calc returned {}", result);
            if (i == 2) {
                log.info("calc result will be manipulated");
                return -2;
            } else {
                log.info("calc result will be passed through");
                return result;
            }
        } catch (Throwable e) {
            log.info("calc exception catched: {}", e.getMessage());
            return -3;
        }
    }

}
