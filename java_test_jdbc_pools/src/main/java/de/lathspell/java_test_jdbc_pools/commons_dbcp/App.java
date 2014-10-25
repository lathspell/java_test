package de.lathspell.java_test_jdbc_pools.commons_dbcp;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

    private static final Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws Exception {
        new App();
    }

    public App() throws Exception {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUsername("postgres");
        ds.setPassword("geheim");
        ds.setUrl("jdbc:postgresql://localhost/postgres");

        ds.setMinIdle(10);
        ds.setMaxIdle(100);
        ds.setMaxActive(100);
        ds.setMinEvictableIdleTimeMillis(60 * 1000);
        ds.setTimeBetweenEvictionRunsMillis(5 * 1000);

        ds.setValidationQuery("SELECT -1");
        // ds.setValidationQueryTimeout(-1);
        // ds.setTestOnBorrow(true);
        // ds.setTestOnReturn(false);
        ds.setTestWhileIdle(false);
        ds.setMaxWait(10000);

        // Force connection close if application fails to do so.
        ds.setRemoveAbandonedTimeout(60);
        ds.setLogAbandoned(true);
        ds.setRemoveAbandoned(true);

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
