package de.lathspell.test.io;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

// Damit der ganze Object-Graph serialisiert werden kann muss jede einzelne
// Klasse die irgendwo als Attribut vor kommt ebenfalls serialisierbar sein!

class BunnyType implements Serializable {
    private static final long serialVersionUID = -3299036643949386127L;

    String name;

    public BunnyType(String name) {
        this.name = name;
    }
    
    public String toString() {
        return name;
    }
}

class Bunny implements Serializable {
    private static final long serialVersionUID = -6814203485633565516L;

    String name;
    BunnyType type;
    int age;
    List<String> attrs;

    public Bunny(String name, BunnyType type, int age, List<String> attrs) {
        this.name = name;
        this.type = type;
        this.age = age;
        this.attrs = attrs;
    }

    public String toString() {
        return name+" is a "+age+" years old "+type+" that's "+attrs;
    }
}

public class SerializableTest {

    @Test
    public void saveAndLoad() throws IOException, ClassNotFoundException {
        File bunnystore = File.createTempFile("bunnystore", "junit");

        // write
        Bunny outbunny = new Bunny("Bugs", new BunnyType("Rabbit"), 14, Arrays.asList("cute", "brown", "fast"));
        new ObjectOutputStream(new FileOutputStream(bunnystore)).writeObject(outbunny);

        // read
        Bunny inbunny = (Bunny) new ObjectInputStream(new FileInputStream(bunnystore)).readObject();

        // compare
        assertEquals(inbunny.toString(), outbunny.toString());
    }

}
