package de.lathspell.java_test_mina.udp3;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientNonsenseHandler extends IoHandlerAdapter {

    private static final Logger log = LoggerFactory.getLogger(ClientNonsenseHandler.class);

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        log.debug("Session opened");

        NonsenseRequestMessage req = new NonsenseRequestMessage();
        req.setText("MyText");
        req.setTwobytes(new byte[] { 0x34, 0x32 });
        req.setVersion(101);

        session.write(req);
        log.warn("!!! SENT: " + req + " !!!");

        log.debug("leave");
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        log.debug("Received message: " + message);

        NonsenseResponseMessage resp = (NonsenseResponseMessage) message;
        log.warn("!!! RECEIVED " + resp + " !!!");

        session.close(false);
        log.debug("leave");
    }
}
