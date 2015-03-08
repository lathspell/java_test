package de.lathspell.java_test_tyrus.speedtest;

import java.io.IOException;

import javax.websocket.ClientEndpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ClientEndpoint
public class MyClientEndpoint {

    private static final Logger log = LoggerFactory.getLogger(MyClientEndpoint.class);

    private int max = 100;
    private int counter = 1;

    /**
     * Every websocket message creates a new class.
     */
    public MyClientEndpoint() {
        log.info(hashCode() + " ctor");
    }

    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) throws Exception {
        log.info(hashCode() + " onOpen: starting download");
        session.getBasicRemote().sendText("data " + counter);
    }

    @OnMessage
    public void onMessage(byte[] message, Session session) throws IOException {
        log.info(hashCode() + " onMessage #" + counter);
        if (counter < max) {
            counter++;
            session.getBasicRemote().sendText("data " + counter);
        } else {
            log.info(hashCode() + " onMessage #" + counter + " finishing download");
            session.close();
        }
    }

    @OnClose
    public void onClose(Session session) throws Exception {
        log.info(hashCode() + " onClose");
    }
}
