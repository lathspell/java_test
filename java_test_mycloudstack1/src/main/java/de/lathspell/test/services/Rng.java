package de.lathspell.test.services;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import de.lathspell.test.generators.RandomDoubleGenerator;

@Service
@Slf4j
public class Rng {

    @Autowired
    private RandomDoubleGenerator rdg;

    @Scheduled(fixedRate = 500L)
    public void rng() {
        log.info("Take " + rdg.nextDouble());
    }

}
