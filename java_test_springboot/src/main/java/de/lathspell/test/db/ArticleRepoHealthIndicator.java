package de.lathspell.test.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * This class is used when /actuator/health is accessed.
 */
@Component
@Slf4j
public class ArticleRepoHealthIndicator implements HealthIndicator {

    @Autowired
    private ArticleRepo articleRepo;

    @Override
    public Health health() {
        log.info("Checking database health");
        try {
            long count = articleRepo.count();
            return Health.up().status("Database is up").withDetail("num_articles", count).build();
        } catch (RuntimeException e) {
            return Health.down().status("Database is down: " + e.getMessage()).build();
        }
    }

}
