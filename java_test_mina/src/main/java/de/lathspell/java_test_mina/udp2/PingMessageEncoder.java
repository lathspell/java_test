package de.lathspell.java_test_mina.udp2;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static de.lathspell.java_test_mina.udp2.MyProtocol.*;

public class PingMessageEncoder implements MessageEncoder<PingMessage> {

    private static final Logger log = LoggerFactory.getLogger(PingMessageEncoder.class);

    @Override
    public void encode(IoSession session, PingMessage message, ProtocolEncoderOutput out) throws Exception {
        log.debug("ping encode enter");

        IoBuffer buffer = IoBuffer.allocate(PING_SIZE, false);
        buffer.putShort(PING_SIGNATURE);
        buffer.fill((byte) 0x20, PING_OFFSET_SERIAL - buffer.position());
        buffer.putShort(message.getSerial());
        buffer.fill((byte) 0x20, PING_SIZE - buffer.position());
        buffer.flip(); // FIXME?
        out.write(buffer);

        log.debug("ping encode leave");
    }
}
