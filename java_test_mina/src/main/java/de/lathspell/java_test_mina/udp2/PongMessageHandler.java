package de.lathspell.java_test_mina.udp2;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.handler.demux.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PongMessageHandler implements MessageHandler<PongMessage> {

    private static final Logger log = LoggerFactory.getLogger(PongMessageHandler.class);

    @Override
    public void handleMessage(IoSession session, PongMessage message) throws Exception {
        log.info("pong handling: " + message);

        log.info("!!! PONG #" + message.getSerial() + " RECEIVED !!!");

        session.close(false);
        log.info("pong session closed, " + session.getReadBytes() + " bytes in / " + session.getWrittenBytes() + " bytes out");

    }
}
