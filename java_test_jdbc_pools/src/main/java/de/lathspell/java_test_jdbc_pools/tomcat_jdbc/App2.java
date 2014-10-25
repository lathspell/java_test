package de.lathspell.java_test_jdbc_pools.tomcat_jdbc;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.DataSourceFactory;
import org.apache.tomcat.jdbc.pool.PoolConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Same as App but configured via java.util.Properties.
 */
public class App2 {

    private static final Logger log = LoggerFactory.getLogger(App2.class);

    public static void main(String[] args) throws Exception {
        new App2();
    }

    public App2() throws Exception {
        // Using properties als alternative to PoolProperties.setXXX()
        // Keys can be looked up in class DataSourceFactory.
        Properties p = new Properties();
        p.put("driverClassName", "org.postgresql.Driver");
        p.put("url", "jdbc:postgresql://localhost/postgres");
        p.put("username", "postgres");
        p.put("password", "geheim");

        // p.setInitialSize(10);
        // p.setMinIdle(2); // Default: 10
        // p.setMaxIdle(8);
        // p.setMaxActive(10); // Default: 100
        // p.setMinEvictableIdleTimeMillis(1 * 1000);
        // p.setTimeBetweenEvictionRunsMillis(1 * 1000);

        // p.setJmxEnabled(true);
        p.put("initSQL", "SET application_name = 'java_test_tomcat_jdbc_pool'");

        // Without a validation query, connections that where closed due to a
        // database crash would never reconnect!
        p.put("validationQuery", "SELECT 1");
        p.put("validationInterval", "5000");
        p.put("testWhileIdle", "true");
        // p.setTestOnConnect(false);
        // p.setTestOnBorrow(true);
        // p.setTestOnReturn(false);
        // p.setMaxWait(10000);

        // Force connection close if application fails to do so.
        // p.setRemoveAbandonedTimeout(60);
        // p.setLogAbandoned(true);
        // p.setRemoveAbandoned(true);

        PoolConfiguration pc = DataSourceFactory.parsePoolProperties(p);
        log.info("Connecting with: " + pc);
        DataSource ds = new DataSource(pc);

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
