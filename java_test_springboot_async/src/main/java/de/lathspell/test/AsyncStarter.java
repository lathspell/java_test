package de.lathspell.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import de.lathspell.test.async.MyTimer;

@Component
@Slf4j
public class AsyncStarter implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private MyTimer myTimer;

    public AsyncStarter() {
        log.info("ctor");
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("Starting Timer");
        myTimer.run();
    }

}
