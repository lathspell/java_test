package de.lathspell.test.lombok;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AtLog {

    public AtLog() {
        log.info("ctor");
    }
}
