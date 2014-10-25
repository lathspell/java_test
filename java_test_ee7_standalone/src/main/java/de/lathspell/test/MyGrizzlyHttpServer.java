package de.lathspell.test;

import javax.ws.rs.core.MediaType;

import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple HTTP Server.
 *
 * Answers GET requests to http://127.0.0.1:4321/ping with "pong".
 */
public class MyGrizzlyHttpServer {

    private static final Logger log = LoggerFactory.getLogger(MyGrizzlyHttpServer.class);

    private static final String DOCUMENT_ROOT = null;

    private static final String HOST = "127.0.0.1";

    private static final int PORT = 4321;

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.createSimpleServer(DOCUMENT_ROOT, HOST, PORT);
        server.getServerConfiguration().addHttpHandler(
                new StaticHttpHandler("src/main/webapp/resources"),
                "/static");
        server.getServerConfiguration().addHttpHandler(
                new HttpHandler() {
                    @Override
                    public void service(Request request, Response response) throws Exception {
                        log.info("Receiving request from " + request.getRemoteAddr());
                        response.setContentType(MediaType.TEXT_PLAIN);
                        response.setCharacterEncoding("UTF-8");
                        response.getWriter().write("pong\n");
                    }

                },
                "/ping");

        server.start();

        System.out.println("press any key to stop the server");
        System.in.read();
    }
}
