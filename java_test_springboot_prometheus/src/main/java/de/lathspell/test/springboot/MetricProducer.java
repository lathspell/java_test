package de.lathspell.test.springboot;

import java.util.Arrays;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MetricProducer {

    @Bean
    MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        return registry -> registry.config().commonTags("my-package", "de.lathspell.test");

    }

    @Component
    public class SampleBean {

        private final Gauge gauge;

        public SampleBean(MeterRegistry registry) {
            Gauge.builder("unhealthy", healthEndpoint,
                e -> e.getIfAvailable().health().getStatus() == Status.UP ? 0.0 : 1.0
        ).register(meterRegistry);
            gauge = registry.gauge("random value", Arrays.asList(Tag.of("type", "order"),
                    Tag.of("status", Order.Status.PAYMENT_EXPECTED.toString())));
        }

        public void handleMessage(String message) {
            this.counter.increment();
            // handle message implementation
        }

    }
}
