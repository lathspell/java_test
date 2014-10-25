package de.lathspell.java_test_mina.udp3;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

import org.apache.mina.core.buffer.IoBuffer;

/**
 * The request packet for my made up nonsense protocol.
 */
public class NonsenseRequestMessage implements MyProtocolMessage {

    public static final short SIGNATURE = 0x3432;
    /** Also used by NonsenseResponseMessage. */
    static final int VERSION = 123;
    private static final int SIZE = 40;
    private static final int OFFSET_VERSION = 8;
    private static final int OFFSET_TWOBYTES = 12;
    private static final int OFFSET_TEXT = 20;
    private static final int SIZE_TEXT = 10;
    /** Some version. */
    int version;
    /** Some bytes. */
    byte[] twobytes;
    /** Text without limit to test buffer overflows (SIZE_TEXT bytes, UTF-8). */
    String text;

    public NonsenseRequestMessage() {
        this.version = VERSION;
    }

    public NonsenseRequestMessage(IoBuffer in) throws Exception {
        version = in.getInt(OFFSET_VERSION);

        byte[] rawText = new byte[SIZE_TEXT];
        in.position(OFFSET_TEXT);
        in.get(rawText, 0, rawText.length);
        text = new String(rawText, "UTF-8");

        twobytes = new byte[2];
        in.position(OFFSET_TWOBYTES);
        in.get(twobytes, 0, twobytes.length);

        in.position(in.limit()); // packet completely decoded
    }

    @Override
    public String toString() {
        return String.format("NonsenseRequest(ver=%d, 2bytes=[%x %x], text=%s)", version, twobytes[0], twobytes[1], text);
    }

    @Override
    public IoBuffer encode() throws Exception {
        IoBuffer buffer = IoBuffer.allocate(SIZE, false);
        buffer.fill(SIZE);
        buffer.putShort(0, SIGNATURE);
        buffer.putInt(OFFSET_VERSION, version);
        buffer.position(OFFSET_TEXT);
        buffer.put(getText().substring(0, text.length() < SIZE_TEXT ? text.length() : SIZE_TEXT).getBytes("UTF-8"));
        buffer.position(OFFSET_TWOBYTES);
        buffer.put(twobytes);
        // finish
        buffer.position(SIZE);
        buffer.flip();
        return buffer;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public byte[] getTwoBytes() {
        return twobytes;
    }

    public void setTwobytes(byte[] twobytes) {
        this.twobytes = twobytes;
    }

    public short getTwoBytesAsShort() {
        ByteBuffer bb = ByteBuffer.allocate(2);
        bb.order(ByteOrder.BIG_ENDIAN);
        bb.put(twobytes[0]);
        bb.put(twobytes[1]);
        bb.position(0);
        return bb.getShort();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
