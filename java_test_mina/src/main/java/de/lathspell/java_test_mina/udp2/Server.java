package de.lathspell.java_test_mina.udp2;

import java.net.InetSocketAddress;

import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.handler.demux.DemuxingIoHandler;
import org.apache.mina.handler.demux.MessageHandler;
import org.apache.mina.transport.socket.nio.NioDatagramAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server {

    private static final String HOSTNAME = "localhost";
    private static final int PORT = 4242;
    private static final Logger log = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) throws Exception {
        log.info("Starting application");
        new Server();
    }

    public Server() throws Exception {
        log.info("");

        NioDatagramAcceptor acceptor = new NioDatagramAcceptor();
        acceptor.getFilterChain().addLast("logger", new LoggingFilter());
        acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new DemuxingProtocolCodecFactory() {
            {
                addMessageDecoder(PingMessageDecoder.class);
                addMessageEncoder(PongMessage.class, PongMessageEncoder.class);
            }
        }));
        acceptor.setHandler(new DemuxingIoHandler() {
            {
                addReceivedMessageHandler(PingMessage.class, new PingMessageHandler());
                addSentMessageHandler(PongMessage.class, MessageHandler.NOOP);
            }
        });
        log.info("Filter chain: " + acceptor.getFilterChain());
        acceptor.bind(new InetSocketAddress(HOSTNAME, PORT));
        log.info("Ready to accept requests on port " + PORT);

        while (acceptor.isActive()) {
            Thread.sleep(1000 * 10);
            log.info("Still listening");
        }
    }
}
