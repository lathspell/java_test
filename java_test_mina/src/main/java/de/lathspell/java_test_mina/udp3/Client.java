package de.lathspell.java_test_mina.udp3;

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

    public enum COMMAND {

        PING, NONSENSE;
    }
    private static final Logger log = LoggerFactory.getLogger(ClientPingHandler.class);
    private static final int CONNECT_TIMEOUT_MS = 1000 * 10;
    private static final String HOSTNAME = "localhost";
    private static final int PORT = 4242;

    public static void main(String[] args) throws Exception {
        Thread.currentThread().setName("Client");
        log.info("Main started with: " + Arrays.deepToString(args));

        COMMAND cmd = null;
        if (args.length != 1) {
            usage();
        } else if (args[0].equals("--ping")) {
            cmd = COMMAND.PING;
        } else if (args[0].equals("--nonsense")) {
            cmd = COMMAND.NONSENSE;
        } else {
            usage();
        }

        new Client(cmd);
    }

    private static void usage() {
        System.out.println("Usage: Client [--ping|--nonsense]");
        System.exit(1);
    }

    public Client(COMMAND cmd) throws Exception {
        log.info("Client does " + cmd);

        MyProtocolCodec coder = new MyProtocolCodec();
        NioDatagramConnector connector = new NioDatagramConnector();
        connector.setConnectTimeoutMillis(CONNECT_TIMEOUT_MS);
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(coder, coder));

        switch (cmd) {
            case PING:
                connector.setHandler(new ClientPingHandler());
                break;
            case NONSENSE:
                connector.setHandler(new ClientNonsenseHandler());
                break;
            default:
                throw new Exception("Unexpected command " + cmd);
        }

        // wait for connection and start session
        ConnectFuture future = connector.connect(new InetSocketAddress(HOSTNAME, PORT));
        future.awaitUninterruptibly();
        IoSession session = future.getSession();

        // wait for the session to terminate and then cleanup
        session.getCloseFuture().awaitUninterruptibly();
        log.info("Client session closed, " + session.getReadBytes() + " bytes read / " + session.getWrittenBytes() + " bytes written");
        connector.dispose();
    }
}
