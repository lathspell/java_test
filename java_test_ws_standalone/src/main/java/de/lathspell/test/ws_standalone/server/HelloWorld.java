package de.lathspell.test.ws_standalone.server;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface HelloWorld {

    String sayHi(@WebParam(name="text") String text);

    String sayHiToUser(String user);

    Set<String> getUserSet();


}
