package de.lathspell.java_test_mina.udp3;

import org.apache.mina.core.buffer.IoBuffer;

public class PingRequestMessage implements MyProtocolMessage {

    public static final short SIGNATURE = 0x3033;
    private static final int SIZE = 96;
    private static final int OFFSET_SERIAL = 20;
    /** Some random number. */
    private short serial;

    public PingRequestMessage(short serial) {
        this.serial = serial;
    }

    public PingRequestMessage(IoBuffer in) {
        this.serial = in.getShort(OFFSET_SERIAL);
        in.position(in.limit()); // packet completely decoded
    }

    @Override
    public String toString() {
        return "PingMessage(" + getSerial() + ")";
    }

    @Override
    public IoBuffer encode() {
        IoBuffer buffer = IoBuffer.allocate(SIZE, false);
        buffer.putShort(SIGNATURE);
        buffer.fill((byte) 0x20, OFFSET_SERIAL - buffer.position());
        buffer.putShort(getSerial());
        // finish
        buffer.position(SIZE);
        buffer.flip();
        return buffer;
    }

    public short getSerial() {
        return serial;
    }

    public void setSerial(short serial) {
        this.serial = serial;
    }
}
