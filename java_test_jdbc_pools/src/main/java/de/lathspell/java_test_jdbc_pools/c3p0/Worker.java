package de.lathspell.java_test_jdbc_pools.c3p0;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Worker implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(Worker.class);
    private ComboPooledDataSource ds;

    public Worker(ComboPooledDataSource ds) {
        this.ds = ds;
    }

    @Override
    public void run() {
        long id = Thread.currentThread().getId();
        long wait = Math.round(Math.random() * 1000 * 4);

        for (int i = 0; i < 9999999; i++) {
            Connection conn = null;
            try {
                conn = ds.getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT " + id + ", pg_sleep(2)");
                rs.next();
                long resultId = rs.getLong(1);
                if (resultId != id) {
                    throw new Exception("Wanted id " + id + " but got " + resultId);
                }
                log.info(String.format("Thread %2d active=%2d/%d idle=%2d unclosed=%2d waiting=%dms",
                        id,
                        ds.getNumConnectionsAllUsers(),
                        ds.getMaxPoolSize(),
                        ds.getNumIdleConnectionsAllUsers(),
                        ds.getNumUnclosedOrphanedConnectionsAllUsers(), wait));

                rs.close();
                st.close();
            } catch (Exception e) {
                // Runnable.run() may not throw Exceptions!
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            try {
                Thread.sleep(wait);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
