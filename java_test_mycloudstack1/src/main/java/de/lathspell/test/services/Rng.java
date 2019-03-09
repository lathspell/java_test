package de.lathspell.test.services;

import java.util.Random;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class Rng {

    private final Random rnd = new Random();

    @Scheduled(fixedRate = 500L)
    public void rng() {
        log.info("Take " + rnd.nextDouble());
    }

}
