package de.lathspell.java_test_mina.udp3;

import org.apache.mina.core.buffer.IoBuffer;

public class PingResponseMessage implements MyProtocolMessage {

    public static final short SIGNATURE = 0x3032;
    private static final int SIZE = 24;
    private static final int OFFSET_SERIAL = 8;
    /** Random number */
    private short serial;

    public PingResponseMessage(short serial) {
        this.serial = serial;
    }

    public PingResponseMessage(IoBuffer in) {
        this.serial = in.getShort(OFFSET_SERIAL);
        in.position(in.limit()); // packet completely decoded
    }

    @Override
    public String toString() {
        return "PongMessage(" + getSerial() + ")";
    }

    /**
     * Encodes this object into its binary representation.
     *
     * This one fills the buffer sequentially, maybe it's easier to
     * fill it with 0 and then always use indexes?
     */
    @Override
    public IoBuffer encode() {
        IoBuffer buffer = IoBuffer.allocate(SIZE, false);
        buffer.putShort(SIGNATURE);
        buffer.fill((byte) 0x20, OFFSET_SERIAL - buffer.position());
        buffer.putShort(getSerial());
        // finish
        buffer.position(SIZE);
        buffer.flip(); // FIXME?
        return buffer;
    }

    public short getSerial() {
        return serial;
    }

    public void setSerial(short serial) {
        this.serial = serial;
    }
}
