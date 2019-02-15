package de.lathspell.test;


import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class Screamer {

    @Scheduled(fixedRate = 1000L)
    public void scream() {
        log.info("Take " + Math.random());
    }
    
}
