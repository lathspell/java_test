package de.lathspell.java_test_mina.udp2;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static de.lathspell.java_test_mina.udp2.MyProtocol.*;

public class PongMessageEncoder implements MessageEncoder<PongMessage> {

    private static final Logger log = LoggerFactory.getLogger(PongMessageEncoder.class);

    @Override
    public void encode(IoSession session, PongMessage message, ProtocolEncoderOutput out) throws Exception {
        log.debug("pong encode enter");

        IoBuffer buffer = IoBuffer.allocate(PONG_SIZE, false);
        buffer.putShort(PONG_SIGNATURE);
        buffer.fill((byte) 0x20, PONG_OFFSET_SERIAL - buffer.position());
        buffer.putShort(message.getSerial());
        buffer.flip(); // FIXME?
        out.write(buffer);

        log.debug("pong encode leave");
    }
}
