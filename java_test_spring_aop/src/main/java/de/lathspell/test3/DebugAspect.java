package de.lathspell.test3;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;

@Slf4j
public class DebugAspect {

    private DebugLog debugLog;

    // Constructor-Injection because we want to play around with old-style XML files that don't honor @Autowired
    public DebugAspect(DebugLog debugLog) {
        this.debugLog = debugLog;
    }

    public void beforeAnyMethod(JoinPoint jp) {  // JoinPoint is the actual method that was matched
        log.info("Before " + jp);

        for (Object p : jp.getArgs()) {
            log.info("Storing parameter " + p.toString());
            debugLog.getAllInputs().add(p);
        }
    }

    public void beforeAnyOtherMethod(JoinPoint jp) {
        log.info("Before " + jp);

        for (Object p : jp.getArgs()) {
            log.info("Storing parameter " + p.toString());
            debugLog.getAllInputs().add(p);
        }
    }
}
