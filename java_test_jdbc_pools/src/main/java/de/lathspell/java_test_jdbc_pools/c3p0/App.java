package de.lathspell.java_test_jdbc_pools.c3p0;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

    private static final Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws Exception {
        new App();
    }

    public App() throws Exception {
        ComboPooledDataSource ds = new ComboPooledDataSource();
        ds.setDriverClass("org.postgresql.Driver"); //loads the jdbc driver
        ds.setJdbcUrl("jdbc:postgresql://localhost/postgres");
        ds.setUser("postgres");
        ds.setPassword("geheim");

        ds.setMinPoolSize(5);
        ds.setMaxPoolSize(20);
        ds.setMaxIdleTime(2);
        ds.setIdleConnectionTestPeriod(1);

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
