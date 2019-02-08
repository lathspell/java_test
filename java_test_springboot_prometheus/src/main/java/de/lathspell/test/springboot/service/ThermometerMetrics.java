package de.lathspell.test.springboot.service;

import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ThermometerMetrics {

    public ThermometerMetrics(MeterRegistry registry, Thermometer thermometer) {
        log.info("Registering 'Temperature' for location=garden in registry " + registry);

        Gauge.builder("Temperature", () -> thermometer.getGardenTemp())
                .baseUnit("C")
                .tag("location", "garden")
                .description("Current garden temperature")
                .register(registry);

    }

}
