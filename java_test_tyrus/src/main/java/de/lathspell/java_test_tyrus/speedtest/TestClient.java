package de.lathspell.java_test_tyrus.speedtest;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.Session;

import org.glassfish.tyrus.client.ClientManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

/**
 * Starts 100 Websocket clients more or less simultaneously.
 */
public class TestClient {

    private static final Logger log = LoggerFactory.getLogger(TestClient.class);

    private static final int numThreads = 4;

    private static final String URI = "ws://localhost:8025/websockets/speedtest";

    public static void main(String[] args) throws Exception {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        // Start Websocket clients. They will connect and wait for a reply in a Grizzly thread.
        List<Session> sessions = new ArrayList<>();
        for (int i = 1; i <= numThreads; i++) {
            ClientManager client = ClientManager.createClient();
            Session session = client.connectToServer(TestClientEndpoint.class, new URI(URI + "?ClntThrd=" + i));
            sessions.add(session);
        }

        // Wait for all open Websocket connections to get an answer after which they close themselves.
        for (Session session : sessions) {
            while (session.isOpen()) {
                log.info("Session " + session.getRequestURI() + " is still busy");
                Thread.sleep(500);
            }
            log.info("Session " + session.getRequestURI() + " has finished");
        }

        log.info("All sessions have finished");
    }

}
