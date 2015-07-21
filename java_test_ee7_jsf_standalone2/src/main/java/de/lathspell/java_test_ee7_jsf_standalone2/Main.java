package de.lathspell.java_test_ee7_jsf_standalone2;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.resource.ResourceCollection;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        // Jetty

        WebAppContext context = new WebAppContext();
        // context.setDescriptor("/WEB-INF/web.xml");
        context.setResourceBase(".");
        context.setContextPath("/ctx");
        context.setBaseResource(
                new ResourceCollection(
                        new String[]{"./src/main/webapp"}));

        Server server = new Server(8080);
        server.setHandler(context);
        server.start();
        server.join();
    }
}
