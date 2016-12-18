package de.lathspell.test;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.servlet.WebappContext;

import de.lathspell.test.webservices.MyServlet;

/**
 * Simple Servlet Server.
 *
 * Answers GET requests to http://127.0.0.1:4321/servlets/myservlet with "pong".
 */
public class MyGrizzlyServletServer {

    private static final String DOCUMENT_ROOT = null;

    private static final String HOST = "127.0.0.1";

    private static final int PORT = 4321;

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.createSimpleServer(DOCUMENT_ROOT, HOST, PORT);

        WebappContext context = new WebappContext("Servlets", "/servlets");
        context.addServlet("MyServlet", MyServlet.class).addMapping("/myservlet");
        context.deploy(server);

        server.start();

        System.out.println("press any key to stop the server");
        System.in.read();
    }
}
