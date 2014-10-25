package de.lathspell.test.text;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringTest {
    @Test
    public void lengthTest() {
        String s = "test";
        String[] sa = { "test1", "test2" };
        
        // Länge eines Strings mit der Funktion .length()
        assertEquals(s.length(), 4);

        // Größe eines Arrays mit dem Attribut .length
        assertEquals(sa.length, 2);
    }
    
    @Test
    public void StringXXX() {
        String string = "abc";
        string.concat("end"); // lost
        assertEquals("abc", string);
        
        StringBuffer sb = new StringBuffer("abc");
        sb.append(3.14152d);
        assertEquals("abc3.14152", sb.toString());
    
        sb.delete(0, 99999).append("test").insert(3, 4).reverse();
        assertEquals("t4set", sb.toString());
    }

    @Test
    public void splitTest() {
        String csv = "";
        String[] lines = csv.split("\n");
        assertEquals(1, lines.length); // buh!
        assertEquals("", lines[0]);
    }
    
}
