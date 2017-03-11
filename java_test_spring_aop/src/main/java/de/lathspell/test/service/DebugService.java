package de.lathspell.test.service;

import lombok.Getter;
import lombok.Setter;
import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Service;

@Service
public class DebugService {

    /** Save last JointPoint for access during testing. */
    public static JoinPoint lastJointPoint;
    
    @Getter
    @Setter
    private String log = "";

    public String append(String entry) {
        if (log.length() != 0) {
            log += "-";
        }
        log += entry;
        return entry;
    }

}
