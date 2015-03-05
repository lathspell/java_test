package de.lathspell.java_test_tyrus;


import java.io.IOException;

import javax.websocket.ClientEndpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ClientEndpoint
public class ClientEndpointAnnotated {

    private static final Logger log = LoggerFactory.getLogger(ClientEndpointAnnotated.class);

    private static final String SENT_MESSAGE = "Hello World";

    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) throws Exception {
        session.getBasicRemote().sendText(SENT_MESSAGE);
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        log.info("Client received message: " + message);
        session.close();
    }

}
