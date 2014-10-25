package de.lathspell.java_test_mina.udp;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MyProtocolCoder implements ProtocolEncoder, ProtocolDecoder {

    public static final int PING_SIZE = 96;
    public static final short PING_SIGNATURE = 0x3033;
    public static final int PING_OFFSET_SERIAL = 20;
    public static final int PONG_SIZE = 24;
    public static final short PONG_SIGNATURE = 0x3032;
    public static final int PONG_OFFSET_SERIAL = 8;
    private static final Logger log = LoggerFactory.getLogger(MyProtocolCoder.class);

    @Override
    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
        log.info("encode " + message.getClass().getSimpleName() + " ...");

        if (message instanceof PongMessage) {
            PongMessage pong = (PongMessage) message;

            IoBuffer buffer = IoBuffer.allocate(PONG_SIZE, false);
            buffer.putShort(PONG_SIGNATURE);
            buffer.fill((byte) 0x20, PONG_OFFSET_SERIAL - buffer.position());
            buffer.putShort(pong.getSerial());
            buffer.flip(); // FIXME?
            out.write(buffer);
        } else if (message instanceof PingMessage) {
            PingMessage ping = (PingMessage) message;

            IoBuffer buffer = IoBuffer.allocate(PING_SIZE, false);
            buffer.putShort(PING_SIGNATURE);
            buffer.fill((byte) 0x20, PING_OFFSET_SERIAL - buffer.position());
            buffer.putShort(ping.getSerial());
            buffer.flip(); // FIXME?
            out.write(buffer);
        } else {
            throw new Exception("Unknown message: " + message.getClass());
        }

        log.info("encode " + message.getClass().getSimpleName() + " finished");
    }

    @Override
    public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
        log.info("decode " + in + " ...");

        short serial;
        switch (in.getShort()) {
            case PING_SIGNATURE:
                serial = in.getShort(PING_OFFSET_SERIAL);
                PingMessage ping = new PingMessage(serial);
                out.write(ping);
                break;
            case PONG_SIGNATURE:
                serial = in.getShort(PONG_OFFSET_SERIAL);
                PongMessage pong = new PongMessage(serial);
                out.write(pong);
                in.position(in.limit());
                break;
            default:
                throw new Exception("Unknown message type: " + in.get(1));
        }

        log.info("decode finished");
    }

    @Override
    public void finishDecode(IoSession session, ProtocolDecoderOutput out) throws Exception {
        log.info("finishDecode");
    }

    @Override
    public void dispose(IoSession session) throws Exception {
        log.info("dispose session " + session);
    }
}
