package de.lathspell.test.observer;

import java.util.Observable;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class Job extends Observable {
    private int id;
    private String cmd;

}
