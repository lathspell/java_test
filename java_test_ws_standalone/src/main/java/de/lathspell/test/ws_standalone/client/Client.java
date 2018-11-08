package de.lathspell.test.ws_standalone.client;

import java.net.URL;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.handler.Handler;

import org.slf4j.bridge.SLF4JBridgeHandler;

import de.lathspell.test.ws_standalone.server.HelloWorld;

public class Client {

    public static void main(String args[]) throws Exception {
        // Leider loggt com.sun nicht mit java.util.logging sondern print?!
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        // Alternativ stdout Ausgabe:
        System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
        // OK: System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
        // ? System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
        //System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");
        QName SERVICE_NAME = new QName("http://server.ws_standalone.test.lathspell.de/", "HelloWorld");
        QName PORT_NAME = new QName("http://server.ws_standalone.test.lathspell.de/", "HelloWorldImplPort");
        String ENDPOINT_ADDRESS = "http://localhost:4242/helloWorld";
        URL WSDL_URL = new URL(ENDPOINT_ADDRESS + "?wsdl");

        Service service = Service.create(WSDL_URL, SERVICE_NAME);

        HelloWorld hw = service.getPort(PORT_NAME, HelloWorld.class);

        // Unseren Logging-Handler einsetzen
        Binding binding = ((BindingProvider) hw).getBinding();
        @SuppressWarnings("rawtypes")
        List<Handler> handlerList = binding.getHandlerChain(); // liefert nur eine Kopie, wtf?
        handlerList.add(new LoggingMessageHandler());
        binding.setHandlerChain(handlerList);

        // SOAP Aufrufe
        System.out.println(hw.sayHi("World"));

        System.out.println(hw.sayHiToUser("World"));
        System.out.println(hw.sayHiToUser("Galaxy"));
        System.out.println(hw.sayHiToUser("Universe"));

        System.out.println();
        System.out.println("User List2: ");
        System.out.println(hw.getUserSet());

    }

}
