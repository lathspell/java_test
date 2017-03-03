package de.lathspell.test;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        log.info("Start logging every 5s");
        int i = 0;
        while (true) {
            log.debug("Debug stuff...");
            Thread.sleep(5 * 1000L);
            if ((i % 10) == 0) {
                log.info("Some Info.");
            }
            if ((i % 50) == 0) {
                log.warn("Big Warning!");
            }
            i++;
        }
    }
}
