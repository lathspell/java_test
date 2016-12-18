package de.lathspell.test.webservices;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class MySoapService {

    @WebMethod
    public String ping() {
        return "pong";
    }
}
