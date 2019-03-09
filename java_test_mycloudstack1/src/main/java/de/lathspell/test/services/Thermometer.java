package de.lathspell.test.services;

import java.util.Random;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class Thermometer {

    private final Random rnd = new Random();

    @Scheduled(fixedRate = 1000L)
    public void temperature1() {
        MDC.put("window", "left");
        log.info("Temperature " + rnd.nextInt(60));
    }

    @Scheduled(fixedRate = 1000L)
    public void temperature2() {
        MDC.put("window", "right"); // same thread as temperature1 - only used for demo purpose
        log.info("Temperature " + rnd.nextInt(60));
    }

}
