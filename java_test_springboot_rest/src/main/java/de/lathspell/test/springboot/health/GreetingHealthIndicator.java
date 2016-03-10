package de.lathspell.test.springboot.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * This thingy gets called when /mgmt/health is accessed.
 */
@Component
public class GreetingHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        double status = Math.random();
        if (status < 0.5) {
            return Health.down().withDetail("Status is at ", status).build();
        }
        return Health.up().withDetail("Status is now at ", status).build();
    }

}
