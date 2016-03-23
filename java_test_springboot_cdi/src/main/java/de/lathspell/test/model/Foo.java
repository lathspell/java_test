package de.lathspell.test.model;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class Foo {

    private final double id = Math.random();

    public Foo() {
        log.info("ctor von {} mit id={}", getClass(), id);
    }

}
