package de.lathspell.test.springboot.service;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class Thermometer {

    @Getter
    @Setter
    private int gardenTemp;

    public Thermometer(MeterRegistry registry) {
        log.info("Registering 'Temperature' for location=garden in registry " + registry);

        Gauge.builder("Temperature", () -> gardenTemp)
                .baseUnit("C")
                .tag("location", "garden")
                .description("Current garden temperature")
                .register(registry);
    }

}
