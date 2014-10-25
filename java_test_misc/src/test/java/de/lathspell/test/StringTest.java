package de.lathspell.test;

import java.util.Arrays;

import org.junit.Test;

public class StringTest {

    @Test
    public void testStrings() {
        String[] s = new String[] { "aaa", "bbb", "ccc" };
        
        System.out.println(s);
        
        System.out.println(s.toString());
        
        System.out.println(Arrays.deepToString(s));
    }
}
