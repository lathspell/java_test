package de.lathspell.java_test_ee7_jsf_standalone2;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        // Jetty

        WebAppContext context = new WebAppContext();
        context.setDescriptor("/WEB-INF/web.xml");
        context.setResourceBase(".");
        context.setContextPath("/ctx");

        Server server = new Server(8080);
        server.setHandler(context);
        server.start();
        server.join();
    }
}
