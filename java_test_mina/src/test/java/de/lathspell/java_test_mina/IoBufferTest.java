package de.lathspell.java_test_mina;

import java.nio.charset.Charset;
import java.util.Arrays;
import org.apache.mina.core.buffer.IoBuffer;
import static org.junit.Assert.*;
import org.junit.Test;

public class IoBufferTest {

    @Test
    public void testIoBuffer() {
        String signature = "FOO";
        byte[] sigBytes = signature.getBytes(Charset.forName("ISO-8859-15"));

        IoBuffer buffer = IoBuffer.allocate(12, false);

        // put + rewind
        assertEquals("HeapBuffer[pos=0 lim=12 cap=12: 00 00 00 00 00 00 00 00 00 00 00 00]", buffer.toString());
        buffer.put(sigBytes);
        assertEquals(0x46, buffer.get(0));
        assertEquals(0x4F, buffer.get(1));
        assertEquals("[70, 79, 79, 0, 0, 0, 0, 0, 0, 0, 0, 0]", Arrays.toString(buffer.buf().array()));
        assertEquals("HeapBuffer[pos=3 lim=12 cap=12: 00 00 00 00 00 00 00 00 00]", buffer.toString());
        buffer.rewind();
        assertEquals("HeapBuffer[pos=0 lim=12 cap=12: 46 4F 4F 00 00 00 00 00 00 00 00 00]", buffer.toString());

        // fill + rewind
        buffer.fill((byte)0xFF, 12);
        buffer.rewind();
        assertEquals("HeapBuffer[pos=0 lim=12 cap=12: FF FF FF FF FF FF FF FF FF FF FF FF]", buffer.toString());

        // put + flip
        buffer.put(sigBytes);
        buffer.flip();
        assertEquals("HeapBuffer[pos=0 lim=3 cap=12: 46 4F 4F]", buffer.toString());

        assertEquals(0x46, buffer.get(0));
        assertEquals(0x4F, buffer.get(1));
    }
}
