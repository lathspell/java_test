package de.lathspell.java_test_mina.udp2;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.handler.demux.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PingMessageHandler implements MessageHandler<PingMessage> {

    private static final Logger log = LoggerFactory.getLogger(PingMessageHandler.class);

    @Override
    public void handleMessage(IoSession session, PingMessage message) throws Exception {
        log.debug("ping handling: " + message);

        log.info("!!! PING #" + message.getSerial() + " RECEIVED !!!");

        PongMessage pong = new PongMessage(message.getSerial());
        session.write(pong);
        log.info("!!! PONG #" + pong.getSerial() + " SENT !!!");

        session.close(false);
        log.debug("ping session closed, " + session.getReadBytes() + " bytes in / " + session.getWrittenBytes() + " bytes out");
    }
}
