package de.lathspell.java_test_mina.udp3;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerHandler extends IoHandlerAdapter {

    private static final Logger log = LoggerFactory.getLogger(ServerHandler.class);

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        log.info("------------------------------------");
        log.info("Session created: " + session.getServiceAddress() + " -> " + session.getRemoteAddress());
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        log.info("Session received: " + message);

        if (message instanceof PingRequestMessage) {
            PingRequestMessage ping = (PingRequestMessage) message;
            PingResponseMessage pong = new PingResponseMessage(ping.getSerial());
            session.write(pong);
        } else if (message instanceof NonsenseRequestMessage) {
            NonsenseRequestMessage req = (NonsenseRequestMessage) message;
            NonsenseResponseMessage resp = new NonsenseResponseMessage();
            resp.setVersion(req.getVersion());
            resp.setText1(req.getText());
            resp.setNr(req.getTwoBytesAsShort());
            session.write(resp);
        } else {
            throw new Exception("Unknown message: " + message.getClass());
        }

        session.close(false);
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        log.info("Session closed with " + session.getReadBytes() + " bytes read / " + session.getWrittenBytes() + " bytes written");
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable e) throws Exception {
        log.warn("Session terminated due to exception!", e);
        session.close(true);
    }
}
