package de.lathspell.test.springboot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * This class starts an async task to update the temperature variable every second.
 */
@Service
@Slf4j
public class WeatherMaker {

    public WeatherMaker(AsyncTaskExecutor taskExecutor, Thermometer thermometer) {
        log.info("ctor");

        log.info("starting async thread for temperature changer");
        taskExecutor.execute(() -> {
            while (true) {
                int temp = new Random().nextInt(40);
                log.debug("WeatherMaker ist adjusting temperature to " + temp);

                thermometer.setGardenTemp(temp);

                try {
                    Thread.sleep(1000L * 1);
                } catch (InterruptedException e) {
                    log.warn("WeatherMaker was interrupted!", e); // on every shutdown
                    return;
                }
            }
        });

        log.info("ctor leave");
    }
}
