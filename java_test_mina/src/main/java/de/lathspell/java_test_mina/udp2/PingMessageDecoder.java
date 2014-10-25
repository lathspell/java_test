package de.lathspell.java_test_mina.udp2;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static de.lathspell.java_test_mina.udp2.MyProtocol.*;

public class PingMessageDecoder implements MessageDecoder {

    private static final Logger log = LoggerFactory.getLogger(PingMessageDecoder.class);

    @Override
    public MessageDecoderResult decodable(IoSession session, IoBuffer in) {
        log.debug("ping decodable?");

        return (in.getShort() == PING_SIGNATURE) ? OK : NOT_OK;
    }

    @Override
    public MessageDecoderResult decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
        log.debug("ping decode enter");

        short serial = in.getShort(PING_OFFSET_SERIAL);
        in.position(PING_SIZE);

        PingMessage ping = new PingMessage(serial);
        out.write(ping);

        log.debug("ping decode leave");
        return OK;
    }

    @Override
    public void finishDecode(IoSession session, ProtocolDecoderOutput out) throws Exception {
        log.debug("ping decode finish");
    }
}
