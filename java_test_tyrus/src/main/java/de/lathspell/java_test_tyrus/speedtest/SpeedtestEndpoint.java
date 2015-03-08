package de.lathspell.java_test_tyrus.speedtest;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.glassfish.tyrus.core.TyrusSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Websocket Endpoint.
 *
 * Each incoming Websocket message might be handled in a different thread.
 * For each Websocket connection, a separated instance of this class is
 * created, though, so private variables can safely be used.
 *
 */
@ServerEndpoint(value = "/speedtest")
public class SpeedtestEndpoint {

    private static final Logger log = LoggerFactory.getLogger(SpeedtestServer.class);

    private final Manager manager = Manager.getInstance();

    private int token;

    private int counter;

    @OnOpen
    public void onOpen(Session session, @PathParam("thread") String remoteThread) throws Exception {
        String ip = ((TyrusSession) session).getRemoteAddr();
        token = manager.start(ip);
        log.info("*{} onOpen from {} of thread {}", token, ip, remoteThread);
        session.getBasicRemote().sendText("TOKEN " + token);
    }

    @OnMessage
    public byte[] onMessage(String message, Session session) throws Exception {
        log.info("*{} onMessage with counter={} received: {}", token, counter, message);

        manager.storeTime(token, counter);
        counter++;

        return RandomData.data;
    }

    @OnClose
    public void onClose(Session session) throws Exception {
        log.info("*{} onClose", token);

        manager.finish(token, counter);
    }
}
