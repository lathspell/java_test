package de.lathspell.test.ws.soap;

import java.net.URL;
import java.util.TreeSet;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class Client {

    public static void main(String args[]) throws Exception {
        URL SERVICE_URL = new URL("http://james.intern:4242/soap");
        QName SERVICE_QNAME = new QName("http://soap.ws.test.lathspell.de/", "VoipWs");
        Service service = Service.create(SERVICE_URL, SERVICE_QNAME);
        VoipWs ws = service.getPort(VoipWs.class);
        ws.sayHi();
        TreeSet<TDO> l;
        l = ws.getDates(42);
        System.out.println("client: Got: "+l);
    }

}
