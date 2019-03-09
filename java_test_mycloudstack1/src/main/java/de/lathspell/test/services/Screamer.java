package de.lathspell.test.services;

import java.util.Random;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class Screamer {

    private final Random rnd = new Random();

    @Async
    @EventListener(ApplicationReadyEvent.class)
    public void screamer() throws InterruptedException {
        Thread.currentThread().setName("ScreamerThread");
        while (true) {
            switch (rnd.nextInt() % 3) {
                case 0:
                    log.info("Info!");
                    break;
                case 1:
                    log.warn("Warning!");
                    break;
                case 2:
                    log.error("Error!");
                    break;
                default:
                    log.info("Whaaat?");
            }
            Thread.sleep(rnd.nextInt(5000));
        }
    }
}
