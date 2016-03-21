package de.lathspell.test.observer;

import java.util.Observable;
import java.util.Observer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Handler2 implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        log.info("Received Upate for " + arg);
    }

}
