package de.lathspell.test;

import java.io.InputStream;

import javax.ws.rs.core.Application;
import javax.ws.rs.ext.RuntimeDelegate;

import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpContainer;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lathspell.test.webservices.MyRestApp;
import de.lathspell.test.webservices.MyRestResource;

/**
 * Simple REST Server with CDI.
 *
 * Answers GET requests to http://127.0.0.1:4321/rest/myresource/cdi with "Nobody".
 *
 * FIXME: Currently always returns "HTTP/1.1 500 Request failed."
 */
public class MyGrizzlyRestCdiServer {

    private static final Logger log = LoggerFactory.getLogger(MyGrizzlyRestCdiServer.class);

    private static final String DOCUMENT_ROOT = null;

    private static final String HOST = "127.0.0.1";

    private static final int PORT = 4321;

    public static void main(String[] args) throws Exception {
        log.info("Starting main()");

        // Init JUL (redirect to SLF4J)
        // Grizzly/HK2 logs some exceptions as DEBUG which are filtered by the
        // JUL default level INFO.
        InputStream julConfig = ClassLoader.getSystemResourceAsStream("logging.properties");
        java.util.logging.LogManager.getLogManager().readConfiguration(julConfig);

        // Init CDI
        Weld weld = new Weld();
        WeldContainer container = weld.initialize();

        MyRestResource r = container.instance().select(MyRestResource.class).get();

        // Get REST application with all injected members using CDI
        Application application = container.instance().select(MyRestApp.class).get();
        log.debug("application={}", application);

        // Container from the org.glassfish.jersey project although "HttpHandler.class" does work as well.
        HttpHandler handler = RuntimeDelegate.getInstance().createEndpoint(application, GrizzlyHttpContainer.class);
        handler.setAllowCustomStatusMessage(true);

        HttpServer server = HttpServer.createSimpleServer(DOCUMENT_ROOT, HOST, PORT);
        server.getServerConfiguration().addHttpHandler(handler, "/rest");

        log.debug("Starting {} {}", server.getServerConfiguration().getHttpServerName(), server.getServerConfiguration().getHttpServerVersion());
        server.start();

        System.out.println("press any key to stop the server");
        System.in.read();

        weld.shutdown();
    }
}
