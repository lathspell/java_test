package de.lathspell.test;

import javax.ws.rs.ext.RuntimeDelegate;

import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpContainer;

import de.lathspell.test.webservices.MyRestApp;

/**
 * Simple REST Server.
 *
 * Answers GET requests to http://127.0.0.1:4321/rest/myresource/ping with "pong".
 */
public class MyGrizzlyRestServer {

    private static final String DOCUMENT_ROOT = null;

    private static final String HOST = "127.0.0.1";

    private static final int PORT = 4321;

    public static void main(String[] args) throws Exception {
        // Container from the org.glassfish.jersey project although "HttpHandler.class" does work as well.
        HttpHandler handler = RuntimeDelegate.getInstance().createEndpoint(new MyRestApp(), GrizzlyHttpContainer.class);

        HttpServer server = HttpServer.createSimpleServer(DOCUMENT_ROOT, HOST, PORT);
        server.getServerConfiguration().addHttpHandler(handler, "/rest");

        server.start();

        System.out.println("press any key to stop the server");
        System.in.read();
    }
}
