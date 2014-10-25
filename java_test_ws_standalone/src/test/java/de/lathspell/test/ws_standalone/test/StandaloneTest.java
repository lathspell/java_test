package de.lathspell.test.ws_standalone.test;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import de.lathspell.test.ws_standalone.server.HelloWorld;
import de.lathspell.test.ws_standalone.server.HelloWorldImpl;

public class StandaloneTest {

    private static final String SERVER_ENDPOINT_ADDRESS = "http://localhost:4242/helloWorld";
    private static final QName SERVICE_NAME = new QName("http://server.ws_standalone.test.lathspell.de/", "HelloWorld");
    private static final QName PORT_NAME = new QName("http://server.ws_standalone.test.lathspell.de/", "HelloWorldImplPort");
    private static Endpoint serverEndpoint;
    private static Service clientService;
    private static HelloWorld clientPort;

    @BeforeClass
    public static void beforeClass() throws MalformedURLException {
        // Start server
        HelloWorldImpl implementor = new HelloWorldImpl();
        serverEndpoint = Endpoint.publish(SERVER_ENDPOINT_ADDRESS, implementor);

        // Prepare client
        URL wsdlUrl = new URL(SERVER_ENDPOINT_ADDRESS + "?wsdl");
        clientService = Service.create(wsdlUrl, SERVICE_NAME);
        clientPort = clientService.getPort(PORT_NAME, HelloWorld.class);
    }

    @AfterClass
    public static void afterClass() {
        serverEndpoint.stop();
    }

    @Test
    public void testMethods() {
        assertEquals("Hello World", clientPort.sayHi("World"));
        assertEquals("Hello Galaxy", clientPort.sayHiToUser("Galaxy"));
        assertEquals("Hello Galaxy", clientPort.sayHiToUser("Galaxy"));
        assertEquals("Hello Universe", clientPort.sayHiToUser("Universe"));
        assertEquals("[Galaxy, Universe]", clientPort.getUserSet().toString());
    }
}
