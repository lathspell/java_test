package de.lathspell.java_test_tyrus.speedtest;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Manager {

    private static final Logger log = LoggerFactory.getLogger(Manager.class);

    /** Singleton instance. */
    private static final Manager instance = new Manager();

    private int token;

    private final Map<Integer, Map<Integer, Long>> times = new HashMap<>();

    public static synchronized Manager getInstance() {
        return instance;
    }

    synchronized int start(String ip) {
        int newToken = token++;
        log.info("Assigning token {} to {}", newToken, ip);
        times.put(newToken, new HashMap<Integer, Long>());
        return newToken;
    }

    synchronized void storeTime(int token, int counter) {
        long t = System.currentTimeMillis();
        times.get(token).put(counter, t);

        if (counter > 1) {
            long dt = (t - times.get(token).get(counter - 1));
            double mbits = (Math.round(RandomData.data.length * 8 / dt * 1000 / 1000000 * 100) / 100.0); // MBit/s round to 2 decimals
            log.info("*{} storeTime: Data {} took {}ms => {} MBit/s", token, (counter - 1), dt, mbits);
        }
    }

    synchronized void finish(int token, int counter) {
        log.info("*{} finish: counter={}", token, counter);
        double avg_mbit_s = 0;
        for (int i = 1; i < counter; i++) {
            double bytes = RandomData.data.length;
            double bits = bytes * 8;
            double mbits = bits / 1000 / 1000;
            double ms = times.get(token).get(i) - times.get(token).get(i - 1);
            double s = ms / 1000;
            double mbit_s = mbits / s;
            avg_mbit_s += mbit_s;
        }
        avg_mbit_s /= (counter - 1);
        log.info("*{} finish: avg message took {} MBit/s", token, String.format("%9.2f", avg_mbit_s));
    }
}
