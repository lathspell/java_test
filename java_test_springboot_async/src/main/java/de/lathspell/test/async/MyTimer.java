package de.lathspell.test.async;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyTimer {

    public MyTimer() {
        log.info("ctor");
    }

    @Async
    @SneakyThrows
    public void run() {
        int i = 0;
        while (true) {
            log.info("Timer at {}", i++);
            Thread.sleep(1000L);
        }
    }

}
