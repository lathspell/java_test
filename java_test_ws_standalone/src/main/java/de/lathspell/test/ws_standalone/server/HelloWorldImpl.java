package de.lathspell.test.ws_standalone.server;

import java.util.Set;
import java.util.TreeSet;

import javax.jws.WebService;
import javax.ws.rs.Produces;

@WebService(endpointInterface = "de.lathspell.test.ws_standalone.server.HelloWorld", serviceName = "HelloWorld")
public class HelloWorldImpl implements HelloWorld {

    Set<String> users = new TreeSet<>();

    @Override
    public String sayHi(String text) {
        return "Hello " + text;
    }

    @Produces("application/json")
    @Override
    public String sayHiToUser(String user) {
        users.add(user);
        return "Hello " + user;
    }

    @Override
    public Set<String> getUserSet() {
        return users;
    }


}
