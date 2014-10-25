package de.lathspell.test.ws_standalone.client;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import de.lathspell.test.ws_standalone.server.HelloWorld;

public class Client {

    public static void main(String args[]) throws Exception {
        QName SERVICE_NAME = new QName("http://server.ws_standalone.test.lathspell.de/", "HelloWorld");
        QName PORT_NAME = new QName("http://server.ws_standalone.test.lathspell.de/", "HelloWorldImplPort");
        String ENDPOINT_ADDRESS = "http://localhost:4242/helloWorld";
        URL WSDL_URL = new URL(ENDPOINT_ADDRESS+"?wsdl");

        Service service = Service.create(WSDL_URL, SERVICE_NAME);

        HelloWorld hw = service.getPort(PORT_NAME, HelloWorld.class);

        System.out.println(hw.sayHi("World"));

        System.out.println(hw.sayHiToUser("World"));
        System.out.println(hw.sayHiToUser("Galaxy"));
        System.out.println(hw.sayHiToUser("Universe"));

        System.out.println();
        System.out.println("User List2: ");
        System.out.println(hw.getUserSet());

    }

}
