package de.lathspell.test.springboot.config;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;

public class GlobalMetricConfiguration {

    /**
     * Global settings that affect all metrics.
     */
    @Bean
    MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        return registry -> registry.config().commonTags("appName", "java_test_springboot_prometheus");
    }

}
