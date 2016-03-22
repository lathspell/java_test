package de.lathspell.test.ws.soap;

import java.util.TreeSet;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;

// Ohne Angabe des serviceName wird es "VoipSoapService"
@WebService(endpointInterface="de.lathspell.test.ws.soap.VoipWs", serviceName="VoipWs")
public class VoipWsImpl implements VoipWs {
    @Override
    public void sayHi() {
        System.out.println("server: sayHi called");
    }

    public static void main(String[] args) {
        Endpoint.publish("http://james.intern:4242/soap", new VoipWsImpl());
    }

    @Override
    public TreeSet<TDO> getDates(int i) {
        // TODO Auto-generated method stub
        TreeSet<TDO> ts = new TreeSet<TDO>();
        ts.add(new TDO("2009-01-02"));
        System.out.println("server: returning: "+ts);
        return ts;
    }
}
