package de.lathspell.java_test_mina.udp2;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import static org.apache.mina.filter.codec.demux.MessageDecoder.NOT_OK;
import static org.apache.mina.filter.codec.demux.MessageDecoder.OK;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static de.lathspell.java_test_mina.udp2.MyProtocol.*;

public class PongMessageDecoder implements MessageDecoder {

    private static final Logger log = LoggerFactory.getLogger(PongMessageDecoder.class);

    @Override
    public MessageDecoderResult decodable(IoSession session, IoBuffer in) {
        log.debug("pong decodable?");
        return (in.getShort() == PONG_SIGNATURE) ? OK : NOT_OK;
    }

    @Override
    public MessageDecoderResult decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
        log.debug("pong decode enter");

        short serial = in.getShort(PONG_OFFSET_SERIAL);
        PongMessage pong = new PongMessage(serial);
        out.write(pong);
        in.position(in.limit());

        log.debug("pong decode leave");
        return OK;
    }

    @Override
    public void finishDecode(IoSession session, ProtocolDecoderOutput out) throws Exception {
        log.debug("pong decode finish");
    }
}
