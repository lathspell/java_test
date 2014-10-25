package de.lathspell.java_test_ee6_daemon;

import java.util.concurrent.Future;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Example of a "Daemon" style, long running method in a separate thread.
 *
 * It's told to be very bad practice to spawn threads inside an application
 * container oneself so the separate thread is forked when an instance of the
 * @Asynchronous annotated MyDaemonLoop should be injected into myDaemonLoop.
 *
 * Its endless loop method is called by this classes @PostConstruct method and
 * stopped gracefully or using interrupts, if necessary, by the @PreDestroy
 * method.
 *
 * Be aware that it runs immediately after Deployment (as supposed) and
 * NetBeans tries to hot-deploy it after every save!
 *
 * When deployed using NetBeans, strange ClosedChannelException have been
 * noticed that did not occur when deploying the .war using the Glassfish
 * web interface.
 *
 * P.S.: Remember that a beans.xml must be present to make EJBs work!
 */
@Startup
@Singleton
public class MyDaemonEJB {

    private static final Logger log = LogManager.getLogger(MyDaemonEJB.class);
    /** The worker threads main loop. */
    @EJB
    private MyDaemonLoop myDaemonLoop;
    /** The Future object that holds a reference to the worker thread. */
    Future f;

    @PostConstruct
    private void postConstruct() {
        log.entry();
        f = myDaemonLoop.start();
        log.info("future: " + f);
        log.exit();
    }

    @PreDestroy
    private void preDestroy() {
        log.entry();
        // Gracefully stopping the Thread
        myDaemonLoop.stop();
        try {
            Thread.sleep(1000 * 3);
        } catch (InterruptedException e) {
            log.warn(e);
        }
        // If that does not work (check threads loop sleep!), interrupt it
        if (!f.isDone()) {
            log.warn("canceling future: " + f);
            f.cancel(true);
        }
        log.exit();
    }
}
