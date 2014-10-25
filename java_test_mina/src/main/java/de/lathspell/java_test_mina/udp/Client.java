package de.lathspell.java_test_mina.udp;

import java.net.InetSocketAddress;
import java.util.Arrays;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioDatagramConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Client {

    private static final Logger log = LoggerFactory.getLogger(ClientHandler.class);
    private static final int CONNECT_TIMEOUT_MS = 1000 * 10;
    private static final String HOSTNAME = "localhost";
    private static final int PORT = 4242;

    public static void main(String[] args) {
        log.info("Started with: " + Arrays.deepToString(args));

        if (args.length != 1) {
            usage();
        }
        if (args[0].equals("--ping")) {
            MyProtocolCoder coder = new MyProtocolCoder();
            NioDatagramConnector connector = new NioDatagramConnector();
            connector.setConnectTimeoutMillis(CONNECT_TIMEOUT_MS);
            connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(coder, coder));
            connector.getFilterChain().addLast("logger", new LoggingFilter());
            connector.setHandler(new ClientHandler());

            // wait for connection and start session
            ConnectFuture future = connector.connect(new InetSocketAddress(HOSTNAME, PORT));
            future.awaitUninterruptibly();
            IoSession session = future.getSession();

            // wait for the session to terminate and then cleanup
            session.getCloseFuture().awaitUninterruptibly();
            connector.dispose();
        } else {
            usage();
        }
    }

    private static void usage() {
        System.out.println("Usage: Client --ping");
        System.exit(1);
    }
}
