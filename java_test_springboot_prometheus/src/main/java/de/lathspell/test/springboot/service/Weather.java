package de.lathspell.test.springboot.service;

import java.util.Random;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * This class starts an async task to update the temperature variable every other second.
 */
@Service
@Slf4j
public class Weather {

    @Autowired
    private Thermometer thermometer;

    @Scheduled(fixedRate = 100 /* [ms] */)
    public void changeWeather() {
        int temp = new Random().nextInt(40);
        log.debug("WeatherMaker ist adjusting temperature to " + temp);
        thermometer.setGardenTemp(temp); // will be read by Gauge

        thermometer.getWeatherChanges().increment();

        if ((temp % 3) == 0) {
            thermometer.getThunders().increment();
        }
    }

}
