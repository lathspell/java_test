package de.lathspell.java_test_mina.udp2;

import java.net.InetSocketAddress;
import java.util.Arrays;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.handler.demux.DemuxingIoHandler;
import org.apache.mina.handler.demux.MessageHandler;
import org.apache.mina.transport.socket.nio.NioDatagramConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static de.lathspell.java_test_mina.udp2.MyProtocol.*;

public class Client {

    private static final Logger log = LoggerFactory.getLogger(Client.class);
    private static final int CONNECT_TIMEOUT_MS = 1000 * 10;
    private static final String HOSTNAME = "localhost";
    private static final int PORT = 4242;

    public static void main(String[] args) {
        log.info("Started with: " + Arrays.deepToString(args));

        short cmd = -1;
        if (args.length != 1) {
            usage();
        } else if (args[0].equals("--ping")) {
            cmd = PING_SIGNATURE;
        } else {
            usage();
        }

        new Client().run(cmd);
    }

    public void run(final short cmd) {
        NioDatagramConnector connector = new NioDatagramConnector();
        connector.setConnectTimeoutMillis(CONNECT_TIMEOUT_MS);
        connector.getFilterChain().addLast("logger", new LoggingFilter());
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new DemuxingProtocolCodecFactory() {
            {
                addMessageEncoder(PingMessage.class, PingMessageEncoder.class);
                addMessageDecoder(PongMessageDecoder.class);
            }
        }));
        connector.setHandler(new DemuxingIoHandler() {
            {
                addSentMessageHandler(PingMessage.class, MessageHandler.NOOP);
                addReceivedMessageHandler(PongMessage.class, new PongMessageHandler());
            }

            @Override
            public void sessionOpened(IoSession session) throws Exception {
                log.info("sessionOpened enter");

                switch (cmd) {
                    case PING_SIGNATURE:
                        short serial = (short) Math.round(Math.random() * Short.MAX_VALUE);
                        PingMessage ping = new PingMessage(serial);
                        session.write(ping);
                        log.info("!!! PING #" + serial + " SENT !!!");
                        break;
                    default:
                        throw new Exception("Unknown command: " + cmd);
                }

                log.info("sessionOpened leave");
            }
        });

        // wait for connection and start session
        log.info("connecting...");
        ConnectFuture future = connector.connect(new InetSocketAddress(HOSTNAME, PORT));
        future.awaitUninterruptibly();
        IoSession session = future.getSession();

        // wait for the session to terminate and then cleanup
        session.getCloseFuture().awaitUninterruptibly();
        log.info("cleaning up");
        connector.dispose();
    }

    private static void usage() {
        System.out.println("Usage: Client --ping");
        System.exit(1);
    }
}
