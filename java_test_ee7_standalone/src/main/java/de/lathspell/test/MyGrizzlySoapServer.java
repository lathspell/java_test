package de.lathspell.test;

import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.jaxws.JaxwsHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lathspell.test.webservices.MySoapService;

/**
 * Simple JAX-WS SOAP Server.
 *
 * Answers requests to the ping() method of http://127.0.0.1:4321/soap with "pong".
 */
public class MyGrizzlySoapServer {

    private static final Logger log = LoggerFactory.getLogger(MyGrizzlyHttpServer.class);

    private static final String HOST = "127.0.0.1";

    private static final int PORT = 4321;

    public static void main(String[] args) throws Exception {

        NetworkListener networkListener = new NetworkListener("jaxws-listener", HOST, PORT);

        HttpHandler httpHandler = new JaxwsHandler(new MySoapService());

        HttpServer httpServer = new HttpServer();
        httpServer.addListener(networkListener);
        httpServer.getServerConfiguration().addHttpHandler(httpHandler, "/soap");

        httpServer.start();

        System.out.println("press any key to stop the server");
        System.in.read();
    }

}
