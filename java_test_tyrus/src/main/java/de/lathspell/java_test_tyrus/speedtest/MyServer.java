package de.lathspell.java_test_tyrus.speedtest;

import java.util.HashMap;
import java.util.Map;

import org.glassfish.tyrus.core.DebugContext;
import org.glassfish.tyrus.core.TyrusWebSocketEngine;
import org.glassfish.tyrus.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class MyServer {

    private static final Logger log = LoggerFactory.getLogger(MyServer.class);

    public static void main(String... args) throws Exception {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        // Adds "X-Tyrus-Tracing" HTTP Header
        Map<String, Object> serverProperties = new HashMap<>();
        serverProperties.put(TyrusWebSocketEngine.TRACING_TYPE, DebugContext.TracingType.ALL);
        serverProperties.put(TyrusWebSocketEngine.TRACING_THRESHOLD, DebugContext.TracingThreshold.TRACE);

        Server server = new Server("localhost", 8025, "/websockets", serverProperties, MyServerEndpoint.class);

        try {
            server.start();

            System.out.println("Please press a key to stop the server!");
            System.in.read();
        } finally {
            server.stop();
        }
    }
}
