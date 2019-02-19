package de.lathspell.test.services;

import java.util.Random;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class Thermometer {

    private final Random rnd = new Random();

    @Scheduled(fixedRate = 1000L)
    public void temperature() {
        log.info("Temperature " + rnd.nextInt(60));
    }

}
