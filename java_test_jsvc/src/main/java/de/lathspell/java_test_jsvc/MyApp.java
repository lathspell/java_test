package de.lathspell.java_test_jsvc;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;

public class MyApp implements Daemon {

    private static Logger log = LogManager.getLogger(MyApp.class);

    @Override
    public void init(DaemonContext context) throws Exception {
        log.entry();
    }

    @Override
    public void start() throws Exception {
        log.entry();
        Thread.sleep(1000 * 1);
        log.info("MyApp is still in start()");
    }

    @Override
    public void stop() throws Exception {
        log.entry();
    }

    @Override
    public void destroy() {
        log.entry();
    }
}
