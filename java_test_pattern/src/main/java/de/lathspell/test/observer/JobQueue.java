package de.lathspell.test.observer;

import java.util.Observable;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JobQueue extends Observable {

    public void addJob(Job j) {
        log.info("Adding job " + j);
        setChanged();
        notifyObservers(j);
        log.info("Finished");
    }
}
