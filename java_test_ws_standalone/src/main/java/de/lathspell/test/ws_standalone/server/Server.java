package de.lathspell.test.ws_standalone.server;

import javax.xml.ws.Endpoint;

public class Server {

    public static void main(String args[]) throws Exception {
        HelloWorldImpl implementor = new HelloWorldImpl();
        String address = "http://localhost:4242/helloWorld";
        Endpoint.publish(address, implementor);

        Thread.sleep(5 * 60 * 1000);
        System.exit(0);
    }

}
