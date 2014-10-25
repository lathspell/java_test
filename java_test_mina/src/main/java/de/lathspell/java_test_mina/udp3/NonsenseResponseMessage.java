package de.lathspell.java_test_mina.udp3;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import org.apache.mina.core.buffer.IoBuffer;

/**
 * The Response packet for my made up nonsense protocol.
 */
public class NonsenseResponseMessage implements MyProtocolMessage {

    public static final short SIGNATURE = 0x3433;
    private static final int SIZE = 60;
    private static final int OFFSET_VERSION = 8;
    private static final int OFFSET_NR = 16;
    private static final int OFFSET_TEXT = 20;
    private static final int SIZE_TEXT = 10;
    /** Some version. */
    int version;
    /** Some text. */
    String text1;
    /** Some short. */
    short nr;

    public NonsenseResponseMessage() {
        this.version = NonsenseRequestMessage.VERSION;
    }

    public NonsenseResponseMessage(IoBuffer in) throws Exception {
        version = in.getInt(OFFSET_VERSION);

        byte[] rawText = new byte[SIZE_TEXT];
        in.position(OFFSET_TEXT);
        in.get(rawText, 0, rawText.length);
        text1 = new String(rawText, "UTF-8");

        byte[] buf = new byte[2];
        in.position(OFFSET_NR);
        in.get(buf, 0, 2);
        nr = Short.parseShort(new String(buf));

        in.position(in.limit()); // packet completely decoded
    }

    @Override
    public String toString() {
        return String.format("NonsenseResponse(ver=%d, text1=%s, nr=%d)", version, text1, nr);
    }

    @Override
    public IoBuffer encode() throws Exception {
        IoBuffer buffer = IoBuffer.allocate(SIZE, false);
        buffer.fill(SIZE);
        buffer.putShort(0, SIGNATURE);
        buffer.putInt(OFFSET_VERSION, getVersion());
        buffer.putShort(OFFSET_NR, getNr());
        buffer.position(OFFSET_TEXT);
        buffer.put(getText1().getBytes("UTF-8"));
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

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public short getNr() {
        return nr;
    }

    public void setNr(short nr) {
        this.nr = nr;
    }
}
