package de.lathspell.java_test_tyrus.speedtest;

import java.security.SecureRandom;
import java.text.DecimalFormat;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.glassfish.tyrus.core.TyrusSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Websocket Endpoint.
 *
 * Each incoming Websocket message might be handled in a different thread.
 * For each Websocket connection, a separated instance of this class is
 * created, though, so private variables can safely be used.
 *
 */
@ServerEndpoint(value = "/speedtest")
public class MyServerEndpoint {

    private static final Logger log = LoggerFactory.getLogger(MyServer.class);

    private static final byte[] data;

    private static final DecimalFormat df = new DecimalFormat("#.00");

    // We use the same random data for all messages.
    static {
        log.info("Generating random data");
        data = new byte[1024 * 1024 * 3];
        new SecureRandom().nextBytes(data);
    }

    private int counter = 1;

    private final long[] times = new long[1000 + 1];

    @OnOpen
    public void onOpen(Session session) throws Exception {
        log.info("#" + hashCode() + " " + session.getQueryString() + " onOpen from " + ((TyrusSession) session).getRemoteAddr());
        times[0] = System.currentTimeMillis();
    }

    @OnMessage
    public byte[] onMessage(String message, Session session) throws Exception {
        log.info("#" + hashCode() + " " + session.getQueryString() + " onMessage with counter=" + counter + " received: " + message);

        long t = System.currentTimeMillis();
        if (counter > 1) {
            long dt = (t - times[counter - 1]);
            double mbits = (Math.round(data.length * 8 / dt * 1000 / 1000000 * 100) / 100.0); // MBit/s round to 2 decimals
            log.info("#" + hashCode() + " " + session.getQueryString() + " onMessage: Data " + (counter - 1) + " took " + dt + "ms => " + mbits);
        }
        times[counter] = t;
        counter++;

        return data;
    }

    @OnClose
    public void onClose(Session session) throws Exception {
        log.info("#" + hashCode() + " " + session.getQueryString() + " onClose");
        long dt = System.currentTimeMillis() - times[0];
        log.info("#" + hashCode() + " " + session.getQueryString() + " onClose: test took " + dt + "ms");

        double avg_mbit_s = 0;
        for (int i = 2; i <= (counter - 1); i++) {
            double b = data.length;
            double bit = b * 8;
            double mbit = bit / 1000 / 1000;
            double ms = times[i] - times[i - 1];
            double s = ms / 1000;
            double mbit_s = mbit / s;
            avg_mbit_s += mbit_s;
        }
        avg_mbit_s /= (counter - 1 - 1);
        log.info("#" + hashCode() + " " + session.getQueryString() + " onClose: avg message took " + String.format("%9.2f", avg_mbit_s) + " MBit/s");
    }
}
