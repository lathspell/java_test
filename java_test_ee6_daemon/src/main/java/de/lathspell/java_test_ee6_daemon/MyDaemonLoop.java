package de.lathspell.java_test_ee6_daemon;

import java.util.concurrent.Future;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Stateless
public class MyDaemonLoop {

    private static final Logger log = LogManager.getLogger(MyDaemonLoop.class);
    private static boolean isActive = true;

    @Asynchronous
    public Future<Integer> start() {
        while (isActive) {
            log.info("looping...");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                log.error(e);
                return new AsyncResult<Integer>(-1);
            }
        }
        return new AsyncResult<Integer>(0);
    }

    public void stop() {
        isActive = false;
    }
}