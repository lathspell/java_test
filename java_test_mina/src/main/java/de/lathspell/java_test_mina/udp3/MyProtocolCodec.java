package de.lathspell.java_test_mina.udp3;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyProtocolCodec implements ProtocolEncoder, ProtocolDecoder {

    private static final Logger log = LoggerFactory.getLogger(MyProtocolCodec.class);

    @Override
    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
        log.info("encode " + message.getClass().getSimpleName());

        out.write(((MyProtocolMessage) message).encode());
    }

    @Override
    public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
        log.info("decode " + in);

        short signature = in.getShort();
        if (signature == PingRequestMessage.SIGNATURE) {
            out.write(new PingRequestMessage(in));
        } else if (signature == PingResponseMessage.SIGNATURE) {
            out.write(new PingResponseMessage(in));
        } else if (signature == NonsenseRequestMessage.SIGNATURE) {
            out.write(new NonsenseRequestMessage(in));
        } else if (signature == NonsenseResponseMessage.SIGNATURE) {
            out.write(new NonsenseResponseMessage(in));
        } else {
            throw new Exception("Unknown message type: " + in.get(1));
        }
    }

    @Override
    public void finishDecode(IoSession session, ProtocolDecoderOutput out) throws Exception {
        // nothing to do
    }

    @Override
    public void dispose(IoSession session) throws Exception {
        // nothing to do
    }
}
