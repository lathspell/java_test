package de.lathspell.java_test_mina.udp3;

import java.net.InetSocketAddress;

import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioDatagramAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server {

    private static final String HOSTNAME = "localhost";
    private static final int PORT = 4242;
    private static final Logger log = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) throws Exception {
        Thread.currentThread().setName("Server");
        log.info("Starting application");
        new Server();
    }

    public Server() throws Exception {
        MyProtocolCodec coder = new MyProtocolCodec(); // encoder and decoder in one class
        ServerHandler handler = new ServerHandler();

        NioDatagramAcceptor acceptor = new NioDatagramAcceptor();
        acceptor.setHandler(handler);
        acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(coder, coder));
        acceptor.bind(new InetSocketAddress(HOSTNAME, PORT));
        log.info("Ready to accept requests on port " + PORT);

        while (acceptor.isActive()) {
            Thread.sleep(1000 * 10);
            log.info("Still listening");
        }
    }
}
