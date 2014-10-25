package de.lathspell.java_test_mina.udp3;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientPingHandler extends IoHandlerAdapter {

    private static final Logger log = LoggerFactory.getLogger(ClientPingHandler.class);

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        log.debug("Session opened");

        short serial = (short) Math.round(Math.random() * Short.MAX_VALUE);
        PingRequestMessage ping = new PingRequestMessage(serial);
        session.write(ping);
        log.warn("!!! PING #" + serial + " SENT !!!");

        log.debug("leave");
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        log.debug("Received message: " + message);

        PingResponseMessage pong = (PingResponseMessage) message;
        log.warn("!!! PONG #" + pong.getSerial() + " RECEIVED !!!");

        session.close(false);
        log.debug("leave");
    }
}
