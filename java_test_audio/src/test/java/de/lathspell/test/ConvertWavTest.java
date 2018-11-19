package de.lathspell.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class ConvertWavTest {

    @Test
    public void convert() throws Exception {

        byte[] input = IOUtils.toByteArray(new FileInputStream("src/test/resources/input.wav"));

        ByteArrayInputStream inputBis = new ByteArrayInputStream(input);

        AudioFormat frmt = new AudioFormat(44100, 8, 1, true, false);

        AudioInputStream ais = new AudioInputStream(inputBis, frmt, input.length);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        AudioSystem.write(ais, AudioFileFormat.Type.WAVE, bos);

        // check
        byte[] output = bos.toByteArray();
        byte[] expected = IOUtils.toByteArray(new FileInputStream("src/test/resources/expected.wav"));
        assertArrayEquals(expected, output);
    }

}
