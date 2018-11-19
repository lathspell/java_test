package de.lathspell.test;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class ConvertWavTest {

    @Test
    public void convert() throws Exception {

        byte[] input = IOUtils.toByteArray(new FileInputStream("src/test/resources/input.wav"));
        ByteArrayInputStream inputBis = new ByteArrayInputStream(input);

        ByteOutputStream bos = convert16to8bit(inputBis);

        byte[] output = bos.getBytes();
        IOUtils.write(output, new FileOutputStream("src/test/resources/output.wav"));
        byte[] expected = IOUtils.toByteArray(new FileInputStream("src/test/resources/expected.wav"));
        assertArrayEquals(expected, output);
    }

    private ByteOutputStream convert16to8bit(InputStream is) throws Exception {
        AudioFormat format = new AudioFormat(44100, 8, 1, true, true);
        AudioInputStream in = AudioSystem.getAudioInputStream(is);
        AudioInputStream convert = AudioSystem.getAudioInputStream(format, in);

        ByteOutputStream bos = new ByteOutputStream();
        AudioSystem.write(convert, AudioFileFormat.Type.WAVE, bos);
        return bos;
    }

}
