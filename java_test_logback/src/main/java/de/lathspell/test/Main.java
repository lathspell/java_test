package de.lathspell.test;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        log.info("Start logging every 5s");
        while (true) {
            log.debug("ping");
            Thread.sleep(5 * 1000L);
        }
    }
}
