package de.lathspell.test.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Handler1 implements Observer {

    public List<Integer> debugList = new ArrayList<>();

    @Override
    public void update(Observable o, Object arg) {
        log.info("Received Upate for " + arg);
        debugList.add(((Job) arg).getId());
    }

}
