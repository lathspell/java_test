package de.lathspell.java_test_tyrus;

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
public class MyClient {

    private static final Logger log = LoggerFactory.getLogger(MyClient.class);

    private static final int max = 100;

    private static final String URI = "ws://localhost:8025/websockets/echo";

    public static void main(String[] args) throws Exception {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        // Start Websocket clients. They will connect and wait for a reply in a Grizzly thread.
        List<Session> sessions = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            ClientManager client = ClientManager.createClient();
            Session session = client.connectToServer(ClientEndpointAnnotated.class, new URI(URI + "?thread=" + i));
            sessions.add(session);
        }

        // Wait for all open Websocket connections to get an answer after which they close themselves.
        for (Session session : sessions) {
            log.info("Waiting for session to finish: " + session.getRequestURI());
            while (session.isOpen()) {
                Thread.sleep(50);
            }
        }

        log.info("All sessions finished");
    }

}
