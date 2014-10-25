package de.lathspell.java_test_mina.udp;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ClientHandler extends IoHandlerAdapter {

    private static final Logger log = LoggerFactory.getLogger(ClientHandler.class);

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        log.info("Session created: " + session);
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        log.info("Session opened");

        short serial = (short) Math.round(Math.random() * Short.MAX_VALUE);
        PingMessage ping = new PingMessage(serial);
        session.write(ping);
        log.info("!!! PING #" + serial + " SENT !!!");
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        log.info("Received message: " + message);

        if (message instanceof PongMessage) {
            PongMessage pong = (PongMessage) message;
            log.info("!!! PONG #" + pong.getSerial() + " RECEIVED !!!");
        } else {
            throw new Exception("Unknown message: " + message.getClass());
        }

        session.close(false);
        log.info("session closed, " + session.getReadBytes() + " bytes in / " + session.getWrittenBytes() + " bytes out");
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        log.info("Session idle");
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        log.info("Session closed");
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable e) throws Exception {
        log.debug("Exception caught, closing session!", e);
        session.close(true);
    }
}
