package de.lathspell.java_test_tyrus.speedtest;

import org.glassfish.tyrus.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class SpeedtestServer {

    private static final Logger log = LoggerFactory.getLogger(SpeedtestServer.class);

    public static void main(String... args) throws Exception {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        Server server = new Server("localhost", 8025, "/websockets", null, SpeedtestEndpoint.class);

        try {
            server.start();

            System.out.println("Please press a key to stop the server!");
            System.in.read();
        } finally {
            server.stop();
        }
    }
}
