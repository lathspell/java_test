package de.lathspell.test.springboot.service;

import io.micrometer.core.instrument.Counter;
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

    @Getter
    private final Counter weatherChanges;
    
    @Getter
    private final Counter thunders;

    public Thermometer(MeterRegistry registry) {
        log.info("Using registry " + registry); // io.micrometer.prometheus.PrometheusMeterRegistry

        // Two different ways to register a Counter?!
        weatherChanges = registry.counter("WeatherChanges");
        thunders = Counter.builder("Thunders").description("Number of thunders").register(registry);

        // This gauge is an anonymous object that is not accessible from outside like the counters.
        // That's not necessary as it just reads the instance variable and does not need further interaction.
        Gauge.builder("Temperature", () -> gardenTemp)
                .baseUnit("C")
                .tag("location", "garden")
                .description("Current garden temperature")
                .register(registry);
    }

}
