package de.lathspell.java_test_tyrus.parallel;

import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.glassfish.tyrus.core.TyrusSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ServerEndpoint(value = "/echo")
public class EchoEndpointAnnotated {

    private static final Logger log = LoggerFactory.getLogger(MyServer.class);

    @OnMessage
    public String onMessage(String message, Session session) throws InterruptedException {
        log.info("onMessage " + session.getQueryString() + " from " + ((TyrusSession) session).getRemoteAddr());
        Thread.sleep((long) (Math.random() * 500));
        return message + " / " + session.getQueryString();
    }
}
