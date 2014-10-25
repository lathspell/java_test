package de.lathspell.java_test_ee7_websockets;

import javax.websocket.OnError;

import org.primefaces.push.EventBus;
import org.primefaces.push.RemoteEndpoint;
import org.primefaces.push.annotation.OnClose;
import org.primefaces.push.annotation.OnMessage;
import org.primefaces.push.annotation.OnOpen;
import org.primefaces.push.annotation.PushEndpoint;
import org.primefaces.push.impl.JSONEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@PushEndpoint("/billboard")
public class BillboardResource {

    private static final Logger log = LoggerFactory.getLogger(BillboardResource.class);

    @OnError
    public void onError(RemoteEndpoint r, EventBus eventBus) {
        log.info("onError for " + r);
    }

    @OnOpen
    public void onOpen(RemoteEndpoint r, EventBus eventBus) {
        log.info("onOpen for " + r);
    }

    @OnClose
    public void onClose(RemoteEndpoint r, EventBus eventBus) {
        log.info("onClose for " + r);
    }

    @OnMessage(encoders = {JSONEncoder.class})
    public String onMessage(RemoteEndpoint r, String message) {
        log.info("onMessage for " + r + ": " + message);
        return message;
    }

}
