package de.lathspell.java_test_jdbc_pools.tomcat_jdbc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

    private static final Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws Exception {
        new App();
    }

    public App() throws Exception {
        PoolProperties p = new PoolProperties();
        p.setDriverClassName("org.postgresql.Driver");
        p.setUrl("jdbc:postgresql://localhost/postgres");
        p.setUsername("postgres");
        p.setPassword("geheim");

        p.setInitialSize(10);
        p.setMinIdle(2);
        p.setMaxIdle(8);
        p.setMaxActive(10);
        p.setMinEvictableIdleTimeMillis(1 * 1000);
        p.setTimeBetweenEvictionRunsMillis(1 * 1000);

        p.setJmxEnabled(true);
        p.setInitSQL("SET application_name = 'java_test_tomcat_jdbc_pool'");

        p.setValidationQuery("SELECT -1");
        p.setValidationInterval(30 * 1000);
        p.setTestOnBorrow(true);
        p.setTestOnReturn(false);
        p.setTestWhileIdle(false);
        p.setMaxWait(10000);

        // Force connection close if application fails to do so.
        p.setRemoveAbandonedTimeout(60);
        p.setLogAbandoned(true);
        p.setRemoveAbandoned(true);

        log.info("Connecting with: " + p);
        DataSource ds = new DataSource();
        ds.setPoolProperties(p);

        // Create thread pool
        ExecutorService executor = Executors.newFixedThreadPool(50);
        // Schedule workers, if more than thread pool size, they get queued.
        for (int i = 0; i < 20; i++) {
            Runnable worker = new Worker(ds);
            executor.execute(worker);
        }
        // Stop accepting new threads and finish existing ones.
        executor.shutdown();
        // Wait until all threads are finished.
        executor.awaitTermination(1, TimeUnit.DAYS);
    }
}
