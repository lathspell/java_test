package de.lathspell.test.io.serializable;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

import org.junit.Test;

public class SerializableTest {

    @Test
    public void saveAndLoad() throws IOException, ClassNotFoundException {
        File bunnystore = File.createTempFile("bunnystore", "junit");

        // write
        Bunny outbunny = new Bunny("Bugs", new BunnySize(20), new BunnyColor("brown"), 14, Arrays.asList("cute", "brown", "fast"));
        new ObjectOutputStream(new FileOutputStream(bunnystore)).writeObject(outbunny);

        // read
        Bunny inbunny = (Bunny) new ObjectInputStream(new FileInputStream(bunnystore)).readObject();

        // compare
        assertEquals(inbunny.toString(), outbunny.toString());
    }

}
