package de.lathspell.test.aspects;

import static java.util.Locale.GERMANY;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.lathspell.test.service.DebugService;

@Aspect
@Component
@Slf4j
public class BazAspects {

    @Autowired
    private DebugService debugService;

    /**
     * Execute as wrapper around the method.
     *
     * This method swaps the arguments and modifies the returned value!
     */
    @Around("execution(public * de.lathspell.test.service.BazService.hello(..)) && args(firstName,lastName)")
    public Object aroundHello(ProceedingJoinPoint jp, String firstName, String lastName) throws Throwable {
        log.info(debugService.append("around/before"));
        String result = (String) jp.proceed(new Object[]{lastName, firstName}); // swapping args
        log.info(debugService.append("around/after"));
        result = result.toUpperCase(GERMANY);
        return result;
    }
}
