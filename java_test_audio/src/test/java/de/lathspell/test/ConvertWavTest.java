package de.lathspell.test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.sound.sampled.AudioFileFormat.Type;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

@Slf4j
public class ConvertWavTest {

    @Test
    public void convert() throws Exception {
        // Read input
        byte[] input = IOUtils.toByteArray(new FileInputStream("src/test/resources/input.wav"));
        ByteArrayInputStream inputBis = new ByteArrayInputStream(input);

        File outputFile = new File("src/test/resources/output.wav");

        // Convert
        AudioInputStream in = AudioSystem.getAudioInputStream(inputBis);
        assertEquals("PCM_SIGNED 44100.0 Hz, 16 bit, mono, 2 bytes/frame, little-endian", in.getFormat().toString());

        AudioFormat targetFormat = new AudioFormat(8000, 8, 1, false, true);
        AudioInputStream converted = AudioSystem.getAudioInputStream(targetFormat, in);
        assertEquals("PCM_UNSIGNED 8000.0 Hz, 8 bit, mono, 1 bytes/frame, ", converted.getFormat().toString());

        // Writing directly as Stream does not work as the header contains the file length and that
        // has not yet calculated.
        // ByteArrayOutputStream bos = new ByteArrayOutputStream();
        // AudioSystem.write(step2, Type.WAVE, bos);
        // Writing as plain file works though because... this method calculates the
        // file length in advance. Now I understand the comment "this class is buggy. Should be replaced in future."
        // from com.sun.media.sound.WaveFileWriter!
        AudioSystem.write(converted, Type.WAVE, outputFile);

        // Test
        byte[] actual = IOUtils.toByteArray(new FileInputStream("src/test/resources/output.wav"));
        byte[] expected = IOUtils.toByteArray(new FileInputStream("src/test/resources/expected.wav"));
        assertArrayEquals(expected, actual);
    }

}
