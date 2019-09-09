package de.lathspell.test.springboot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

import static org.springframework.boot.WebApplicationType.NONE;

@SpringBootApplication
@Slf4j
public class Application implements CommandLineRunner, ApplicationListener<ContextClosedEvent> {

    private boolean inDaemonMode = true;

    private boolean daemonFinished = false;

    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(Application.class);
        app.setWebApplicationType(NONE);
        app.run(args);
    }

    /** Runner that does its work in an endless loop. */
    @Override
    public void run(String... args) throws Exception {
        log.info("Runner started");
        while (inDaemonMode) {
            log.info("Runner working...");
            Thread.sleep(1000); // ms
        }
        log.info("Runner finished");
        daemonFinished = true;
    }

    /** Signal Handler for SIGTERM that asks Runner to graceful shutdown. */
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        log.info("Signal Handler will ask Runner to stop");
        inDaemonMode = false;
        while (!daemonFinished) {
            log.info("Signal Handler waiting for Runner to stop...");
            try {
                Thread.sleep(500); // ms
            } catch (InterruptedException e) {
                log.info("Thread.sleep() interrupted!", e);
            }
        }
        log.info("Signal Handler noticed that Runner has stopped");
    }

}
